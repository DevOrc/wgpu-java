package com.noahcharlton.wgpuj.jni;

import com.noahcharlton.wgpuj.util.WgpuJavaStruct;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;

/** NOTE: THIS FILE WAS PRE-GENERATED BY JNR_GEN! */
public class WgpuRenderPassDepthStencilDescriptor extends WgpuJavaStruct {

    private final Struct.Unsigned64 attachment = new Struct.Unsigned64();
    private final Struct.Enum<WgpuLoadOp> depthLoadOp = new Struct.Enum<>(WgpuLoadOp.class);
    private final Struct.Enum<WgpuStoreOp> depthStoreOp = new Struct.Enum<>(WgpuStoreOp.class);
    private final Struct.Float clearDepth = new Struct.Float();
    private final Struct.Boolean depthReadOnly = new Struct.Boolean();
    private final Struct.Enum<WgpuLoadOp> stencilLoadOp = new Struct.Enum<>(WgpuLoadOp.class);
    private final Struct.Enum<WgpuStoreOp> stencilStoreOp = new Struct.Enum<>(WgpuStoreOp.class);
    private final Struct.Unsigned32 clearStencil = new Struct.Unsigned32();
    private final Struct.Boolean stencilReadOnly = new Struct.Boolean();

    private WgpuRenderPassDepthStencilDescriptor(){}

    @Deprecated
    public WgpuRenderPassDepthStencilDescriptor(Runtime runtime){
        super(runtime);
    }

    /**
    * Creates this struct on the java heap.
    * In general, this should <b>not</b> be used because these structs
    * cannot be directly passed into native code. 
    */
    public static WgpuRenderPassDepthStencilDescriptor createHeap(){
        return new WgpuRenderPassDepthStencilDescriptor();
    }

    /**
    * Creates this struct in direct memory.
    * This is how most structs should be created (unless, they
    * are members of a nothing struct)
    * 
    * @see WgpuJavaStruct#useDirectMemory
    */
    public static WgpuRenderPassDepthStencilDescriptor createDirect(){
        var struct = new WgpuRenderPassDepthStencilDescriptor();
        struct.useDirectMemory();
        return struct;
    }


    public long getAttachment(){
        return attachment.get();
    }

    public void setAttachment(long x){
        this.attachment.set(x);
    }

    public WgpuLoadOp getDepthLoadOp(){
        return depthLoadOp.get();
    }

    public void setDepthLoadOp(WgpuLoadOp x){
        this.depthLoadOp.set(x);
    }

    public WgpuStoreOp getDepthStoreOp(){
        return depthStoreOp.get();
    }

    public void setDepthStoreOp(WgpuStoreOp x){
        this.depthStoreOp.set(x);
    }

    public float getClearDepth(){
        return clearDepth.get();
    }

    public void setClearDepth(float x){
        this.clearDepth.set(x);
    }

    public boolean getDepthReadOnly(){
        return depthReadOnly.get();
    }

    public void setDepthReadOnly(boolean x){
        this.depthReadOnly.set(x);
    }

    public WgpuLoadOp getStencilLoadOp(){
        return stencilLoadOp.get();
    }

    public void setStencilLoadOp(WgpuLoadOp x){
        this.stencilLoadOp.set(x);
    }

    public WgpuStoreOp getStencilStoreOp(){
        return stencilStoreOp.get();
    }

    public void setStencilStoreOp(WgpuStoreOp x){
        this.stencilStoreOp.set(x);
    }

    public long getClearStencil(){
        return clearStencil.get();
    }

    public void setClearStencil(long x){
        this.clearStencil.set(x);
    }

    public boolean getStencilReadOnly(){
        return stencilReadOnly.get();
    }

    public void setStencilReadOnly(boolean x){
        this.stencilReadOnly.set(x);
    }

}