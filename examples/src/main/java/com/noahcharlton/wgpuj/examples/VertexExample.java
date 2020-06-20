package com.noahcharlton.wgpuj.examples;

import com.noahcharlton.wgpuj.core.Device;
import com.noahcharlton.wgpuj.core.ShaderData;
import com.noahcharlton.wgpuj.core.WgpuCore;
import com.noahcharlton.wgpuj.core.WgpuGraphicApplication;
import com.noahcharlton.wgpuj.core.graphics.BlendDescriptor;
import com.noahcharlton.wgpuj.core.graphics.ColorState;
import com.noahcharlton.wgpuj.core.graphics.GraphicApplicationSettings;
import com.noahcharlton.wgpuj.core.graphics.RasterizationState;
import com.noahcharlton.wgpuj.core.graphics.RenderPipelineSettings;
import com.noahcharlton.wgpuj.core.util.Color;
import com.noahcharlton.wgpuj.jni.Wgpu;
import com.noahcharlton.wgpuj.jni.WgpuBlendFactor;
import com.noahcharlton.wgpuj.jni.WgpuBlendOperation;
import com.noahcharlton.wgpuj.jni.WgpuCullMode;
import com.noahcharlton.wgpuj.jni.WgpuFrontFace;
import com.noahcharlton.wgpuj.jni.WgpuIndexFormat;
import com.noahcharlton.wgpuj.jni.WgpuInputStepMode;
import com.noahcharlton.wgpuj.jni.WgpuPrimitiveTopology;
import com.noahcharlton.wgpuj.jni.WgpuTextureFormat;
import com.noahcharlton.wgpuj.jni.WgpuVertexBufferAttributeDescriptor;
import com.noahcharlton.wgpuj.jni.WgpuVertexBufferLayoutDescriptor;
import com.noahcharlton.wgpuj.jni.WgpuVertexFormat;

public class VertexExample {

    private static final float[] VERTICES = new float[]{
            .5f, -.2f, 0f,
            .5f, .2f, 0f,
            .2f, .5f, 0f,
            -.2f, .5f, 0f,
            -.5f, .2f, 0f,
            -.5f, -.2f, 0f,
            -.2f, -.5f, 0f,
            .2f, -.5f, 0f,
            0f, 0f, 0f
    };

    private static final short[] INDICES = new short[]{
            1, 8, 0,
            2, 8, 1,
            3, 8, 2,
            4, 8, 3,
            5, 8, 4,
            6, 8, 5,
            7, 8, 6,
            0, 8, 7
    };

    private static final int FLOATS_PER_VERTEX = 3;

    public static void main(String[] args){
        WgpuCore.loadWgpuNative();

        RenderPipelineSettings renderPipelineSettings = createPipelineSettings();
        GraphicApplicationSettings appSettings = new GraphicApplicationSettings("Wgpu-Java vertex example", 302, 302);

        try(var application = WgpuGraphicApplication.create(appSettings)) {
            Device device = application.getDevice();

            var vertexBuffer = device.createVertexBuffer("Vertex Buffer", VERTICES);
            var indexBuffer = device.createIndexBuffer("Index Buffer", INDICES);

            application.init(renderPipelineSettings);

            while(!application.getWindow().isCloseRequested()){
                var renderPass = application.renderStart();
                renderPass.setIndexBuffer(indexBuffer);
                renderPass.setVertexBuffer(vertexBuffer, 0);

                renderPass.drawIndexed(INDICES.length, 1, 0);

                application.renderEnd();
            }
        }
    }

    private static RenderPipelineSettings createPipelineSettings(){
        ShaderData vertex = ShaderData.fromRawClasspathFile("/vertex.vert", "main");
        ShaderData fragment = ShaderData.fromRawClasspathFile("/vertex.frag", "main");

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
                .setBufferLayouts(new WgpuVertexBufferLayoutDescriptor(
                        Float.BYTES * FLOATS_PER_VERTEX,
                        WgpuInputStepMode.VERTEX,
                        new WgpuVertexBufferAttributeDescriptor(0, WgpuVertexFormat.FLOAT3, 0)))
                .setSampleCount(1)
                .setSampleMask(0)
                .setAlphaToCoverage(false)
                .setBindGroupLayouts()
                .setClearColor(Color.BLACK);
    }
}
