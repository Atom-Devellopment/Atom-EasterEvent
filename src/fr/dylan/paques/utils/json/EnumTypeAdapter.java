package fr.dylan.paques.utils.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {
  private final Map<String, T> nameToConstant = new HashMap<>();
  
  private final Map<T, String> constantToName = new HashMap<>();
  
  public EnumTypeAdapter(Class<T> classOfT) {
    try {
      byte b;
      int i;
      Enum[] arrayOfEnum;
      for (i = (arrayOfEnum = (Enum[])classOfT.getEnumConstants()).length, b = 0; b < i; ) {
        Enum enum_ = arrayOfEnum[b];
        String name = enum_.name();
        SerializedName annotation = classOfT.getField(name).<SerializedName>getAnnotation(SerializedName.class);
        if (annotation != null)
          name = annotation.value(); 
        this.nameToConstant.put(name, (T)enum_);
        this.constantToName.put((T)enum_, name);
        b++;
      } 
    } catch (NoSuchFieldException noSuchFieldException) {}
  }
  
  public T read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      return null;
    } 
    return this.nameToConstant.get(in.nextString());
  }
  
  public void write(JsonWriter out, T value) throws IOException {
    out.value((value == null) ? null : this.constantToName.get(value));
  }
  
  public static final TypeAdapterFactory ENUM_FACTORY = newEnumTypeHierarchyFactory();
  
  public static <TT> TypeAdapterFactory newEnumTypeHierarchyFactory() {
    return new TypeAdapterFactory() {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
          Class<? super T> rawType = typeToken.getRawType();
          if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class)
            return null; 
          if (!rawType.isEnum())
            rawType = rawType.getSuperclass(); 
          return new EnumTypeAdapter<>((Class)rawType);
        }
      };
  }
}