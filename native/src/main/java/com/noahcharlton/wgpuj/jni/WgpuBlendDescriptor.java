package com.noahcharlton.wgpuj.jni;

import com.noahcharlton.wgpuj.util.WgpuJavaStruct;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;

/** NOTE: THIS FILE WAS PRE-GENERATED BY JNR_GEN! */
public class WgpuBlendDescriptor extends WgpuJavaStruct {

    private final Struct.Enum<WgpuBlendFactor> srcFactor = new Struct.Enum<>(WgpuBlendFactor.class);
    private final Struct.Enum<WgpuBlendFactor> dstFactor = new Struct.Enum<>(WgpuBlendFactor.class);
    private final Struct.Enum<WgpuBlendOperation> operation = new Struct.Enum<>(WgpuBlendOperation.class);

    private WgpuBlendDescriptor(){}

    @Deprecated
    public WgpuBlendDescriptor(Runtime runtime){
        super(runtime);
    }

    public static WgpuBlendDescriptor createHeap(){
        return new WgpuBlendDescriptor();
    }

    public static WgpuBlendDescriptor createDirect(){
        var struct = new WgpuBlendDescriptor();
        struct.useDirectMemory();
        return struct;
    }


    public WgpuBlendFactor getSrcFactor(){
        return srcFactor.get();
    }

    public void setSrcFactor(WgpuBlendFactor x){
        this.srcFactor.set(x);
    }

    public WgpuBlendFactor getDstFactor(){
        return dstFactor.get();
    }

    public void setDstFactor(WgpuBlendFactor x){
        this.dstFactor.set(x);
    }

    public WgpuBlendOperation getOperation(){
        return operation.get();
    }

    public void setOperation(WgpuBlendOperation x){
        this.operation.set(x);
    }

}