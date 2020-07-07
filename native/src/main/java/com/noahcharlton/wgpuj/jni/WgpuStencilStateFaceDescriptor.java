package com.noahcharlton.wgpuj.jni;

import com.noahcharlton.wgpuj.util.WgpuJavaStruct;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;

/** NOTE: THIS FILE WAS PRE-GENERATED BY JNR_GEN! */
public class WgpuStencilStateFaceDescriptor extends WgpuJavaStruct {

    private final Struct.Enum<WgpuCompareFunction> compare = new Struct.Enum<>(WgpuCompareFunction.class);
    private final Struct.Enum<WgpuStencilOperation> failOp = new Struct.Enum<>(WgpuStencilOperation.class);
    private final Struct.Enum<WgpuStencilOperation> depthFailOp = new Struct.Enum<>(WgpuStencilOperation.class);
    private final Struct.Enum<WgpuStencilOperation> passOp = new Struct.Enum<>(WgpuStencilOperation.class);

    private WgpuStencilStateFaceDescriptor(){}

    @Deprecated
    public WgpuStencilStateFaceDescriptor(Runtime runtime){
        super(runtime);
    }

    /**
    * Creates this struct on the java heap.
    * In general, this should <b>not</b> be used because these structs
    * cannot be directly passed into native code. 
    */
    public static WgpuStencilStateFaceDescriptor createHeap(){
        return new WgpuStencilStateFaceDescriptor();
    }

    /**
    * Creates this struct in direct memory.
    * This is how most structs should be created (unless, they
    * are members of a nothing struct)
    * 
    * @see WgpuJavaStruct#useDirectMemory
    */
    public static WgpuStencilStateFaceDescriptor createDirect(){
        var struct = new WgpuStencilStateFaceDescriptor();
        struct.useDirectMemory();
        return struct;
    }


    public WgpuCompareFunction getCompare(){
        return compare.get();
    }

    public void setCompare(WgpuCompareFunction x){
        this.compare.set(x);
    }

    public WgpuStencilOperation getFailOp(){
        return failOp.get();
    }

    public void setFailOp(WgpuStencilOperation x){
        this.failOp.set(x);
    }

    public WgpuStencilOperation getDepthFailOp(){
        return depthFailOp.get();
    }

    public void setDepthFailOp(WgpuStencilOperation x){
        this.depthFailOp.set(x);
    }

    public WgpuStencilOperation getPassOp(){
        return passOp.get();
    }

    public void setPassOp(WgpuStencilOperation x){
        this.passOp.set(x);
    }

}