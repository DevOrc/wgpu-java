package com.noahcharlton.wgpuj.examples;

import com.noahcharlton.wgpuj.core.*;
import com.noahcharlton.wgpuj.core.graphics.*;
import com.noahcharlton.wgpuj.core.math.MathUtils;
import com.noahcharlton.wgpuj.core.math.MatrixUtils;
import com.noahcharlton.wgpuj.core.util.*;
import com.noahcharlton.wgpuj.jni.*;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.util.Random;

public class BoidExample {

    private static final float[] VERTICES = new float[]{
            0f, -.01f,
            0f, .01f,
            .04f, 0f,
    };

    private static final int BOID_COUNT = 1200;
    private static final long FLOATS_PER_VERTEX = 2;

    private static final float COHESION_RANGE_SQR = 0.1f * 0.1f;
    private static final float COHESION_SCALE = .020f;
    private static final float SEPARATION_RANGE_SQR = .025f * .025f;
    private static final float SEPARATION_SCALE = .05f;
    private static final float ALIGNMENT_RANGE_SQR = .025f * .025f;
    private static final float ALIGNMENT_SCALE = .005f;
    private static final float MAX_VELOCITY = .1f;
    private static final float DELTA_TIME = .04f;

    static class Boid {
        Vector2f position = new Vector2f(0, 0);
        Vector2f velocity = new Vector2f(0, 0);
    }

    private final WgpuGraphicApplication app;
    private final Device device;
    private final Queue queue;
    private final Window window;
    private final Buffer vertexBuffer;
    private final Boid[] boids;
    private final Buffer boidPositionBuffer;
    private final RenderPipeline pipeline;

    private final long bindGroup;

    public BoidExample(GraphicApplicationConfig config) {
        app = WgpuGraphicApplication.create(config);
        device = app.getDevice();
        window = app.getWindow();
        boids = createBoids();
        queue = app.getDefaultQueue();

        vertexBuffer = device.createVertexBuffer("Vertex Buffer", VERTICES);
        boidPositionBuffer = device.createFloatBuffer("Boid Position Buffer", getBoidPositionData(),
                BufferUsage.STORAGE, BufferUsage.COPY_DST);

        var bindGroupLayout = device.createBindGroupLayout("Bind Group Layout",
                BindGroupUtils.partialLayout(0, Wgpu.ShaderStage.VERTEX, WgpuBindingType.STORAGE_BUFFER));

        bindGroup = device.createBindGroup("Bind Group", bindGroupLayout,
                BindGroupUtils.bufferEntry(0, boidPositionBuffer));

        var pipelineConfig = createPipelineConfig(device);
        pipelineConfig.setBindGroupLayouts(bindGroupLayout);

        pipeline = device.createRenderPipeline(pipelineConfig);
        app.initializeSwapChain();
        run();
    }

    private Boid[] createBoids() {
        var boids = new Boid[BOID_COUNT];
        var rng = new Random();

        for (int i = 0; i < boids.length; i++) {
            var boid = new Boid();
            boid.position.x = 2 * rng.nextFloat() - 1f;
            boid.position.y = 2 * rng.nextFloat() - 1f;
            boid.velocity.x = .2f * rng.nextFloat() -.1f;
            boid.velocity.y = .2f * rng.nextFloat() - .1f;

            boids[i] = boid;
        }

        return boids;
    }

    private float[] getBoidPositionData() {
        Matrix4f matrix = new Matrix4f();
        float[] output = new float[boids.length * 16];

        for (int i = 0; i < boids.length; i++) {
            float angle = (float) Math.atan2(boids[i].velocity.y, boids[i].velocity.x);

            matrix.translate(boids[i].position.x, boids[i].position.y, 0);
            matrix.rotate(angle, MathUtils.UNIT_Z);

            var matrixFloats = MatrixUtils.toFloats(matrix);
            System.arraycopy(matrixFloats, 0, output, i * 16, 16);
            MatrixUtils.reset(matrix);
        }

        return output;
    }

    private void run() {
        while (!window.isCloseRequested()) {
            render();
            update();
        }

        app.close();
    }

    private void update() {
        Vector2f cohesion = new Vector2f();
        int cohesionCount = 0;
        Vector2f separation = new Vector2f();
        Vector2f alignment = new Vector2f();
        int alignmentCount = 0;
        Vector2f temp = new Vector2f();

        for (Boid currentBoid : boids) {
            for (Boid boid : boids) {
                if(boid != currentBoid){
                    float lSqr = temp.set(boid.position).sub(currentBoid.position).lengthSquared();

                    if(lSqr < SEPARATION_RANGE_SQR){
                        separation.sub(temp);
                    }

                    if(lSqr < ALIGNMENT_RANGE_SQR){
                        alignment.add(boid.velocity);
                        alignmentCount++;
                    }

                    if(lSqr < COHESION_RANGE_SQR){
                        cohesion.add(boid.position);
                        cohesionCount++;
                    }
                }

            }

            if(alignmentCount > 0) alignment.div(alignmentCount);
            alignment.mul(ALIGNMENT_SCALE);

            if(cohesionCount > 0) cohesion.div(cohesionCount);
            cohesion.sub(currentBoid.position).mul(COHESION_SCALE);

            separation.mul(SEPARATION_SCALE);

            currentBoid.velocity.add(cohesion).add(alignment).add(separation);
            currentBoid.velocity = currentBoid.velocity.normalize()
                    .mul(MathUtils.clamp(currentBoid.velocity.length(), 0f, MAX_VELOCITY));

            if(Math.abs(currentBoid.position.x) > 1f){
                currentBoid.position.x *= -1;
            }

            if(Math.abs(currentBoid.position.y) > 1f){
                currentBoid.position.y *= -1;
            }

            currentBoid.position.add(new Vector2f(currentBoid.velocity).mul(DELTA_TIME));

            cohesion.zero();
            separation.zero();
            alignment.zero();
            alignmentCount = 0;
            cohesionCount = 0;
        }

        queue.writeFloatsToBuffer(boidPositionBuffer, getBoidPositionData());
    }

    private void render() {
        var renderPass = app.renderStart(Color.BLACK);
        renderPass.setPipeline(pipeline);
        renderPass.setBindGroup(0, bindGroup);
        renderPass.setVertexBuffer(vertexBuffer, 0);
        renderPass.draw(3, boids.length);

        app.renderEnd();
        app.update();
    }

    public static void main(String[] args) {
        WgpuCore.loadWgpuNative();

        var config = new GraphicApplicationConfig("Boid Simulation", 640, 640);

        new BoidExample(config);
    }

    private static RenderPipelineConfig createPipelineConfig(Device device) {
        var vertex = ShaderConfig.fromRawClasspathFile("/boid.vert", "main");
        var fragment = ShaderConfig.fromRawClasspathFile("/boid.frag", "main");

        return new RenderPipelineConfig()
                .setVertexStage(device.createShaderModule(vertex))
                .setFragmentStage(device.createShaderModule(fragment))
                .setRasterizationState(RasterizationState.of(
                        WgpuFrontFace.CCW,
                        WgpuCullMode.NONE,
                        0,
                        0.0f,
                        0.0f))
                .setPrimitiveTopology(WgpuPrimitiveTopology.TRIANGLE_LIST)
                .setColorStates(new ColorState(
                        WgpuTextureFormat.BGRA8_UNORM,
                        new BlendDescriptor(WgpuBlendFactor.ONE, WgpuBlendFactor.ZERO, WgpuBlendOperation.ADD),
                        new BlendDescriptor(WgpuBlendFactor.ONE, WgpuBlendFactor.ZERO, WgpuBlendOperation.ADD),
                        Wgpu.ColorWrite.ALL).build())
                .setDepthStencilState(null)
                .setVertexIndexFormat(WgpuIndexFormat.UINT16)
                .setBufferLayouts(Buffer.createLayout(
                        FLOATS_PER_VERTEX * Float.BYTES,
                        WgpuInputStepMode.VERTEX,
                        Buffer.vertexAttribute(0, WgpuVertexFormat.FLOAT2, 0)
                ))
                .setSampleCount(1)
                .setSampleMask(0)
                .setAlphaToCoverage(false)
                .setBindGroupLayouts();
    }
}
