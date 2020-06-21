package com.noahcharlton.wgpuj.jni;

import com.noahcharlton.wgpuj.util.WgpuJavaStruct;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;

/** NOTE: THIS FILE WAS PRE-GENERATED BY JNR_GEN! */
public class WgpuExtent3d extends WgpuJavaStruct {

    private final Struct.Unsigned32 width = new Struct.Unsigned32();
    private final Struct.Unsigned32 height = new Struct.Unsigned32();
    private final Struct.Unsigned32 depth = new Struct.Unsigned32();

    private WgpuExtent3d(){}

    @Deprecated
    public WgpuExtent3d(Runtime runtime){
        super(runtime);
    }

    public static WgpuExtent3d createHeap(){
        return new WgpuExtent3d();
    }

    public static WgpuExtent3d createDirect(){
        var struct = new WgpuExtent3d();
        struct.useDirectMemory();
        return struct;
    }


    public long getWidth(){
        return width.get();
    }

    public void setWidth(long x){
        this.width.set(x);
    }

    public long getHeight(){
        return height.get();
    }

    public void setHeight(long x){
        this.height.set(x);
    }

    public long getDepth(){
        return depth.get();
    }

    public void setDepth(long x){
        this.depth.set(x);
    }

}