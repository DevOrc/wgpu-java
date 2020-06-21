package com.noahcharlton.wgpuj.jni;

import com.noahcharlton.wgpuj.WgpuJava;
import com.noahcharlton.wgpuj.util.WgpuJavaStruct;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;

/** NOTE: THIS FILE WAS PRE-GENERATED BY JNR_GEN! */
public class WgpuRenderPipelineDescriptor extends WgpuJavaStruct {

    private final Struct.Unsigned64 layout = new Struct.Unsigned64();
    private final WgpuProgrammableStageDescriptor vertexStage = inner(WgpuProgrammableStageDescriptor.createHeap());
    private final Struct.StructRef<WgpuProgrammableStageDescriptor> fragmentStage = new Struct.StructRef<>(WgpuProgrammableStageDescriptor.class);
    private final Struct.Enum<WgpuPrimitiveTopology> primitiveTopology = new Struct.Enum<>(WgpuPrimitiveTopology.class);
    private final Struct.StructRef<WgpuRasterizationStateDescriptor> rasterizationState = new Struct.StructRef<>(WgpuRasterizationStateDescriptor.class);
    private final Struct.StructRef<WgpuColorStateDescriptor> colorStates = new Struct.StructRef<>(WgpuColorStateDescriptor.class);
    private final Struct.Unsigned64 colorStatesLength = new Struct.Unsigned64();
    private final Struct.StructRef<WgpuDepthStencilStateDescriptor> depthStencilState = new Struct.StructRef<>(WgpuDepthStencilStateDescriptor.class);
    private final WgpuVertexStateDescriptor vertexState = inner(WgpuVertexStateDescriptor.createHeap());
    private final Struct.Unsigned32 sampleCount = new Struct.Unsigned32();
    private final Struct.Unsigned32 sampleMask = new Struct.Unsigned32();
    private final Struct.Boolean alphaToCoverageEnabled = new Struct.Boolean();

    private WgpuRenderPipelineDescriptor(){}

    @Deprecated
    public WgpuRenderPipelineDescriptor(Runtime runtime){
        super(runtime);
    }

    public static WgpuRenderPipelineDescriptor createHeap(){
        return new WgpuRenderPipelineDescriptor();
    }

    public static WgpuRenderPipelineDescriptor createDirect(){
        var struct = new WgpuRenderPipelineDescriptor();
        struct.useDirectMemory();
        return struct;
    }


    public long getLayout(){
        return layout.get();
    }

    public void setLayout(long x){
        this.layout.set(x);
    }

    public WgpuProgrammableStageDescriptor getVertexStage(){
        return vertexStage;
    }

    public Struct.StructRef<WgpuProgrammableStageDescriptor> getFragmentStage(){
        return fragmentStage;
    }

    public void setFragmentStage(WgpuProgrammableStageDescriptor... x){
        if(x.length == 0 || x[0] == null){
            this.fragmentStage.set(WgpuJava.createNullPointer());
        } else {
            this.fragmentStage.set(x);
        }
    }

    public WgpuPrimitiveTopology getPrimitiveTopology(){
        return primitiveTopology.get();
    }

    public void setPrimitiveTopology(WgpuPrimitiveTopology x){
        this.primitiveTopology.set(x);
    }

    public Struct.StructRef<WgpuRasterizationStateDescriptor> getRasterizationState(){
        return rasterizationState;
    }

    public void setRasterizationState(WgpuRasterizationStateDescriptor... x){
        if(x.length == 0 || x[0] == null){
            this.rasterizationState.set(WgpuJava.createNullPointer());
        } else {
            this.rasterizationState.set(x);
        }
    }

    public Struct.StructRef<WgpuColorStateDescriptor> getColorStates(){
        return colorStates;
    }

    public void setColorStates(WgpuColorStateDescriptor... x){
        if(x.length == 0 || x[0] == null){
            this.colorStates.set(WgpuJava.createNullPointer());
        } else {
            this.colorStates.set(x);
        }
    }

    public long getColorStatesLength(){
        return colorStatesLength.get();
    }

    public void setColorStatesLength(long x){
        this.colorStatesLength.set(x);
    }

    public Struct.StructRef<WgpuDepthStencilStateDescriptor> getDepthStencilState(){
        return depthStencilState;
    }

    public void setDepthStencilState(WgpuDepthStencilStateDescriptor... x){
        if(x.length == 0 || x[0] == null){
            this.depthStencilState.set(WgpuJava.createNullPointer());
        } else {
            this.depthStencilState.set(x);
        }
    }

    public WgpuVertexStateDescriptor getVertexState(){
        return vertexState;
    }

    public long getSampleCount(){
        return sampleCount.get();
    }

    public void setSampleCount(long x){
        this.sampleCount.set(x);
    }

    public long getSampleMask(){
        return sampleMask.get();
    }

    public void setSampleMask(long x){
        this.sampleMask.set(x);
    }

    public boolean getAlphaToCoverageEnabled(){
        return alphaToCoverageEnabled.get();
    }

    public void setAlphaToCoverageEnabled(boolean x){
        this.alphaToCoverageEnabled.set(x);
    }

}