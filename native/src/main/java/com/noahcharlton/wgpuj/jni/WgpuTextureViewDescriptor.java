package com.noahcharlton.wgpuj.jni;

import com.noahcharlton.wgpuj.util.WgpuJavaStruct;
import com.noahcharlton.wgpuj.util.CStrPointer;
import com.noahcharlton.wgpuj.util.RustCString;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;

/** NOTE: THIS FILE WAS PRE-GENERATED BY JNR_GEN! */
public class WgpuTextureViewDescriptor extends WgpuJavaStruct {

    private final @CStrPointer Struct.Pointer label = new Struct.Pointer();
    private final Struct.Enum<WgpuTextureFormat> format = new Struct.Enum<>(WgpuTextureFormat.class);
    private final Struct.Enum<WgpuTextureViewDimension> dimension = new Struct.Enum<>(WgpuTextureViewDimension.class);
    private final Struct.Enum<WgpuTextureAspect> aspect = new Struct.Enum<>(WgpuTextureAspect.class);
    private final Struct.Unsigned32 baseMipLevel = new Struct.Unsigned32();
    private final Struct.Unsigned32 levelCount = new Struct.Unsigned32();
    private final Struct.Unsigned32 baseArrayLayer = new Struct.Unsigned32();
    private final Struct.Unsigned32 arrayLayerCount = new Struct.Unsigned32();

    private WgpuTextureViewDescriptor(){}

    @Deprecated
    public WgpuTextureViewDescriptor(Runtime runtime){
        super(runtime);
    }

    /**
    * Creates this struct on the java heap.
    * In general, this should <b>not</b> be used because these structs
    * cannot be directly passed into native code. 
    */
    public static WgpuTextureViewDescriptor createHeap(){
        return new WgpuTextureViewDescriptor();
    }

    /**
    * Creates this struct in direct memory.
    * This is how most structs should be created (unless, they
    * are members of a nothing struct)
    * 
    * @see WgpuJavaStruct#useDirectMemory
    */
    public static WgpuTextureViewDescriptor createDirect(){
        var struct = new WgpuTextureViewDescriptor();
        struct.useDirectMemory();
        return struct;
    }


    public java.lang.String getLabel(){
        return RustCString.fromPointer(label.get());
    }

    public void setLabel(java.lang.String x){
        this.label.set(RustCString.toPointer(x));
    }

    public WgpuTextureFormat getFormat(){
        return format.get();
    }

    public void setFormat(WgpuTextureFormat x){
        this.format.set(x);
    }

    public WgpuTextureViewDimension getDimension(){
        return dimension.get();
    }

    public void setDimension(WgpuTextureViewDimension x){
        this.dimension.set(x);
    }

    public WgpuTextureAspect getAspect(){
        return aspect.get();
    }

    public void setAspect(WgpuTextureAspect x){
        this.aspect.set(x);
    }

    public long getBaseMipLevel(){
        return baseMipLevel.get();
    }

    public void setBaseMipLevel(long x){
        this.baseMipLevel.set(x);
    }

    public long getLevelCount(){
        return levelCount.get();
    }

    public void setLevelCount(long x){
        this.levelCount.set(x);
    }

    public long getBaseArrayLayer(){
        return baseArrayLayer.get();
    }

    public void setBaseArrayLayer(long x){
        this.baseArrayLayer.set(x);
    }

    public long getArrayLayerCount(){
        return arrayLayerCount.get();
    }

    public void setArrayLayerCount(long x){
        this.arrayLayerCount.set(x);
    }

}