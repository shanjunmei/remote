package com.ffzx.remote.server;
import java.util.HashSet;
import java.util.Set;

     
 /**
  * 
 * @ClassName: TypeUtils 
 * @Description: 类型工具 
 * @author 李淼淼  445052471@qq.com
 * @date 2016年5月31日 下午4:38:35
  */
public class TypeUtils {
    
    
    private final static Set<Class<?>> TYPES = new HashSet<Class<?>>();
    
    
    static {
	TYPES.add(String.class);
	TYPES.add(Boolean.class);
	TYPES.add(boolean.class);
	TYPES.add(Character.class);
	TYPES.add(char.class);
	TYPES.add(Byte.class);
	TYPES.add(byte.class);
	TYPES.add(Short.class);
	TYPES.add(short.class);
	TYPES.add(Integer.class);
	TYPES.add(int.class);
	TYPES.add(Long.class);
	TYPES.add(long.class);
	TYPES.add(Float.class);
	TYPES.add(float.class);
	TYPES.add(Double.class);
	TYPES.add(double.class);
    }

    public static boolean isWrapperType(Class<?> clazz) {
	return TYPES.contains(clazz);
    }

}
 