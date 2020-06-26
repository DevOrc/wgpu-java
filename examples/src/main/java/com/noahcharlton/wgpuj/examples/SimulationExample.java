package com.noahcharlton.wgpuj.examples;

import com.noahcharlton.wgpuj.WgpuJava;
import com.noahcharlton.wgpuj.core.Device;
import com.noahcharlton.wgpuj.core.ShaderData;
import com.noahcharlton.wgpuj.core.WgpuCore;
import com.noahcharlton.wgpuj.core.WgpuGraphicApplication;
import com.noahcharlton.wgpuj.core.graphics.*;
import com.noahcharlton.wgpuj.core.math.MathUtils;
import com.noahcharlton.wgpuj.core.math.MatrixUtils;
import com.noahcharlton.wgpuj.core.util.*;
import com.noahcharlton.wgpuj.jni.*;
import jnr.ffi.Pointer;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.util.Random;

public class SimulationExample {

    private static final float[] VERTICES = new float[]{
            0f, -4f,
            0f, 4f,
            16f, 0f,
    };

    private static final int PEOPLE_COUNT = 100;
    private static final long FLOATS_PER_VERTEX = 2;

    static class Person{
        final Matrix4f matrix = new Matrix4f();
        boolean infected = false;


        Vector2f position = new Vector2f(0, 0);
        float angle;
        float angularVelocity;
        float velocity;

        public Matrix4f updateMatrix(){
            matrix.zero().m00(1.0f).m11(1.0f).m22(1.0f).m33(1.0f);
            matrix.translate(position.x, position.y, 0);
            matrix.rotate(angle, MathUtils.UNIT_Z);

            return matrix;
        }

        Color getColor(){
            return infected ? Color.RED : Color.GREEN;
        }
    }

    private final WgpuGraphicApplication app;
    private final Device device;
    private final Window window;
    private final Buffer vertexBuffer;
    private final Buffer projMatrixBuffer;
    private final Matrix4f viewMatrix;
    private final Person[] people;
    private final Buffer peoplePosBuffer;
    private final Buffer peopleColorBuffer;

    private final long bindGroup;

    public SimulationExample(GraphicApplicationSettings settings) {
        app = WgpuGraphicApplication.create(settings);
        device = app.getDevice();
        window = app.getWindow();
        viewMatrix = new Matrix4f();
        people = createPeople();

        vertexBuffer = device.createVertexBuffer("Vertex Buffer", VERTICES);
        var projMatrix = createProjectionMatrix();
        projMatrixBuffer = device.createFloatBuffer("Projection Matrix buffer", MatrixUtils.toFloats(projMatrix),
                BufferUsage.UNIFORM, BufferUsage.COPY_DST);
        peoplePosBuffer = device.createFloatBuffer("People Matrices Buffer", getPeoplePositions(),
                BufferUsage.STORAGE, BufferUsage.COPY_DST);
        peopleColorBuffer = device.createFloatBuffer("People Color Buffer", getPeopleColors(), BufferUsage.STORAGE);

        var bindGroupLayout = device.createBindGroupLayout("Bind Group Layout",
                BindGroupUtils.partialLayout(0, Wgpu.ShaderStage.VERTEX, WgpuBindingType.UNIFORM_BUFFER),
                BindGroupUtils.partialLayout(1, Wgpu.ShaderStage.VERTEX, WgpuBindingType.STORAGE_BUFFER),
                BindGroupUtils.partialLayout(2, Wgpu.ShaderStage.VERTEX, WgpuBindingType.STORAGE_BUFFER));

        bindGroup = device.createBindGroup("Bind Group", bindGroupLayout,
                new WgpuBindGroupEntry().setBuffer(0, projMatrixBuffer.getId(), projMatrixBuffer.getSize()),
                new WgpuBindGroupEntry().setBuffer(1, peoplePosBuffer.getId(), peoplePosBuffer.getSize()),
                new WgpuBindGroupEntry().setBuffer(2, peopleColorBuffer.getId(), peopleColorBuffer.getSize()));


        var pipelineSettings = createPipelineSettings();
        pipelineSettings.setBindGroupLayouts(bindGroupLayout);

        app.init(pipelineSettings);
        run();
    }

    private Person[] createPeople() {
        var people = new Person[PEOPLE_COUNT];
        var rng = new Random();

        for(int i = 0; i < people.length; i++){
            var person = new Person();
            person.position.x = rng.nextInt(600) - 300;
            person.position.y = rng.nextInt(600) - 300;
            person.angle = rng.nextFloat() * 2 * MathUtils.PIf;
            person.velocity = rng.nextFloat() + .5f;
            person.infected = rng.nextBoolean();

            people[i] = person;
        }

        return people;
    }

    private float[] getPeopleColors() {
        float[] output = new float[people.length * 4];

        for(int i = 0; i < people.length; i++){
            var color = people[i].getColor();

            System.arraycopy(color.toFloats(), 0, output, i * 4, 4);
        }

        return output;
    }

    private float[] getPeoplePositions(){
        float[] output = new float[people.length * 16];

        for(int i = 0; i < people.length; i++){

            var person = MatrixUtils.toFloats(people[i].updateMatrix());

            System.arraycopy(person, 0, output, i * 16, 16);
        }

        return output;
    }

    private void run() {
        while(!window.isCloseRequested()){
            render();
            update();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        app.close();
    }

    private void update() {
        Vector2f target = calcAveragePosition();

        for(Person person : people){
            var angle = wrapAngle(angleBetween(target, person.position));
            var errorAngle = wrapAngle(person.angle - angle);

            person.angularVelocity = errorAngle < 0 ? .1f : -.1f;
            person.angularVelocity *= Math.abs(errorAngle) * .2f;

            person.angle = wrapAngle(person.angle + person.angularVelocity);
            person.position.x += Math.cos(person.angle) * person.velocity;
            person.position.y += Math.sin(person.angle) * person.velocity;
        }

        updateBuffers();
    }

    private Vector2f calcAveragePosition() {
        var pos = new Vector2f();

        for(Person person : people){
            pos.add(person.position);
        }

        return pos.div(people.length);
    }

    private float wrapAngle(float angle) {
        while(Math.abs(angle) > Math.PI){
            angle -= Math.PI * 2 * Math.signum(angle);
        }

        return angle;
    }

    private float angleBetween(Vector2f x1, Vector2f x2){
        float angle = (float) Math.atan2(x1.y - x2.y, x1.x - x2.x);

        return angle < 0 ? angle + MathUtils.PIf * 2 : angle;
    }

    private void updateBuffers() {
        long queue = device.getDefaultQueue();
        var matrix = createTransformationMatrix();

        Pointer matrixData = WgpuJava.createDirectPointer(16 * Float.BYTES);
        matrixData.put(0, MatrixUtils.toFloats(matrix), 0, 16);

        Pointer peoplePosData = WgpuJava.createDirectPointer(16 * Float.BYTES * people.length);
        peoplePosData.put(0, getPeoplePositions(), 0, 16 * people.length);

        WgpuJava.wgpuNative.wgpu_queue_write_buffer(queue, projMatrixBuffer.getId(), 0, matrixData, 16 * Float.BYTES);
        WgpuJava.wgpuNative.wgpu_queue_write_buffer(queue, peoplePosBuffer.getId(), 0, peoplePosData,
                16 * Float.BYTES * people.length);
    }

    private void render() {
        var renderPass = app.renderStart();
        renderPass.setBindGroup(0, bindGroup);
        renderPass.setVertexBuffer(vertexBuffer, 0);
        renderPass.draw(3, people.length);

        app.renderEnd();
    }

    private Matrix4f createTransformationMatrix() {
        return MatrixUtils.generateMatrix(createProjectionMatrix(), viewMatrix);
    }

    private Matrix4f createProjectionMatrix() {
        Dimension dimension = app.getWindow().getWindowDimension();
        var projectionWidth = dimension.getWidth() / 2f;
        var projectionHeight = dimension.getHeight() / 2f;

        return new Matrix4f().ortho2D(-projectionWidth, projectionWidth,
                -projectionHeight, projectionHeight);
    }

    public static void main(String[] args) {
        WgpuCore.loadWgpuNative();

        var settings = new GraphicApplicationSettings("Pandemic Simulation", 640, 480);

        new SimulationExample(settings);
    }

    private static RenderPipelineSettings createPipelineSettings(){
        ShaderData vertex = ShaderData.fromRawClasspathFile("/simulation.vert", "main");
        ShaderData fragment = ShaderData.fromRawClasspathFile("/simulation.frag", "main");

        return new RenderPipelineSettings()
                .setVertexStage(vertex)
                .setFragmentStage(fragment)
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
                .setBindGroupLayouts()
                .setClearColor(Color.BLACK);
    }
}
