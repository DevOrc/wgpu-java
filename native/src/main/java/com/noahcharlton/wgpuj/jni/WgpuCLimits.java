package com.noahcharlton.wgpuj.jni;

import com.noahcharlton.wgpuj.util.WgpuJavaStruct;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;

/** NOTE: THIS FILE WAS PRE-GENERATED BY JNR_GEN! */
public class WgpuCLimits extends WgpuJavaStruct {

    private final Struct.Unsigned32 maxBindGroups = new Struct.Unsigned32();

    private WgpuCLimits(){}

    @Deprecated
    public WgpuCLimits(Runtime runtime){
        super(runtime);
    }

    public static WgpuCLimits createHeap(){
        return new WgpuCLimits();
    }

    public static WgpuCLimits createDirect(){
        var struct = new WgpuCLimits();
        struct.useDirectMemory();
        return struct;
    }


    public long getMaxBindGroups(){
        return maxBindGroups.get();
    }

    public void setMaxBindGroups(long x){
        this.maxBindGroups.set(x);
    }

}