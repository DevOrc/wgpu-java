package com.noahcharlton.wgpuj.jni;

import com.noahcharlton.wgpuj.util.WgpuJavaStruct;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;

/** NOTE: THIS FILE WAS PRE-GENERATED BY JNR_GEN! */
public class WgpuRequestAdapterOptions extends WgpuJavaStruct {

    private final Struct.Enum<WgpuPowerPreference> powerPreference = new Struct.Enum<>(WgpuPowerPreference.class);
    private final Struct.Unsigned64 compatibleSurface = new Struct.Unsigned64();

    private WgpuRequestAdapterOptions(){}

    @Deprecated
    public WgpuRequestAdapterOptions(Runtime runtime){
        super(runtime);
    }

    public static WgpuRequestAdapterOptions createHeap(){
        return new WgpuRequestAdapterOptions();
    }

    public static WgpuRequestAdapterOptions createDirect(){
        var struct = new WgpuRequestAdapterOptions();
        struct.useDirectMemory();
        return struct;
    }


    public WgpuPowerPreference getPowerPreference(){
        return powerPreference.get();
    }

    public void setPowerPreference(WgpuPowerPreference x){
        this.powerPreference.set(x);
    }

    public long getCompatibleSurface(){
        return compatibleSurface.get();
    }

    public void setCompatibleSurface(long x){
        this.compatibleSurface.set(x);
    }

}