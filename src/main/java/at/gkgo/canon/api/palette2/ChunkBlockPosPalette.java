package at.gkgo.canon.api.palette2;

import at.gkgo.canon.Canon;
import at.gkgo.canon.api.codec.CodecUtils;
import at.gkgo.canon.api.codec.EndecUtils;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.wispforest.owo.serialization.Endec;
import io.wispforest.owo.serialization.endec.StructEndecBuilder;
import it.unimi.dsi.fastutil.ints.Int2IntRBTreeMap;
import net.minecraft.util.Util;
import net.minecraft.util.collection.Int2ObjectBiMap;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Iterator;

public class ChunkBlockPosPalette<T> {
    public Int2ObjectBiMap<T> palette;
    public Int2IntRBTreeMap contents;

    public ChunkBlockPosPalette(Int2ObjectBiMap<T> palette, Int2IntRBTreeMap contents) {
        this.palette = palette;
        this.contents = contents;
    }
    public ChunkBlockPosPalette(){
        this(Int2ObjectBiMap.create(64),new Int2IntRBTreeMap());
    }
    public T get(BlockPos pos){
        if(!contents.containsKey(getId(pos))){
            return null;
        }
        return palette.get(contents.get(getId(pos)));
    }
    public void put(BlockPos pos, T value){
        int i;
        if(palette.size() != 0 && palette.contains(value)){
            i = palette.getRawId(value);
        }else{
            i = palette.add(value);
        }
        contents.put(getId(pos),i);
    }
    public void remove(BlockPos pos){
        contents.remove(getId(pos));
    }
    public static int getId(BlockPos pos){
        return (pos.getY() * 16 + pos.getX()) * 16 + pos.getZ();
    }
    public static BlockPos get(int id){
        return new BlockPos(id >> 4 & 0xf,id >> 8,id & 0xf);
    }
    public void cleanup(){
        Int2IntRBTreeMap cache = new Int2IntRBTreeMap();
        Int2ObjectBiMap<T> newPalette = Int2ObjectBiMap.create(palette.size() / 2);
        contents.replaceAll((c, v) -> cache.computeIfAbsent(v, (b) -> {
            return newPalette.add(palette.get(b));
        }));
        palette = newPalette;
    }
    public static <T>Endec<ChunkBlockPosPalette<T>> endec(Endec<T> wrapped){
        return StructEndecBuilder.of(
               Endec.map(Object::toString,Integer::parseInt,wrapped).xmap((m) -> {
//                    Canon.LOGGER.info("deserializing palette");
                    Int2ObjectBiMap<T> newPalette = Int2ObjectBiMap.create(m.size() + 1);
                    for(var n: m.entrySet()){
                        newPalette.put(n.getValue(),n.getKey());
                    }
                    return newPalette;
                },(p) -> Util.make(new HashMap<>(),(m) -> {
                    if(p == null){
                        return;
                    }
//                    Canon.LOGGER.info("serializing palette");
                    int j = 0;
                    for (T a : p) {
                        if (a != null) m.put(j, a);
                        j++;
                    }
                })).fieldOf("palette",(ChunkBlockPosPalette<T> p) -> p.palette),
                Endec.map(Object::toString,Integer::parseInt,Endec.INT).xmap((a) -> {
//                    Canon.LOGGER.info("deserializing contents");
                    return new Int2IntRBTreeMap(a);
                },(a) -> {
//                    Canon.LOGGER.info("serializing contents");
                    return a;
                }).fieldOf("contents",(ChunkBlockPosPalette<T> p) -> p.contents)
       ,ChunkBlockPosPalette::new);
    }
//    public static<T> Codec<ChunkBlockPosPalette<T>> codec(Codec<T> wrapped){
//        return RecordCodecBuilder.create((i) -> i.group(
//                Codec.unboundedMap(CodecUtils.STRINT,wrapped).xmap((m) -> {
//                    Canon.LOGGER.info("deserializing palette");
//                    Int2ObjectBiMap<T> newPalette = Int2ObjectBiMap.create(m.size());
//                    for(var n: m.entrySet()){
//                        newPalette.put(n.getValue(),n.getKey());
//                    }
//                    return newPalette;
//                },(p) -> Util.make(new HashMap<>(),(m) -> {
//                    if(p == null){
//                        return;
//                    }
//                    Canon.LOGGER.info("serializing palette");
//                    int j = 0;
//                    for (T a : p) {
//                        if (a != null) m.put(j, a);
//                        j++;
//                    }
//                })).fieldOf("palette").forGetter((p) -> p.palette),
//                Codec.unboundedMap(CodecUtils.STRINT,Codec.INT).xmap((a) -> {
//                    Canon.LOGGER.info("deserializing contents");
//                    return new Int2IntRBTreeMap(a);
//                },(a) -> {
//                    Canon.LOGGER.info("serializing contents");
//                    return a;
//                }).fieldOf("contents").forGetter((p) -> p.contents)
//        ).apply(i,ChunkBlockPosPalette::new));
//    }
}
