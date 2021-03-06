/**
 * jetbrick-template
 * http://subchen.github.io/jetbrick-template/
 *
 * Copyright 2010-2013 Guoqiang Chen. All rights reserved.
 * Email: subchen@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jetbrick.template.parser.support;

import java.util.HashSet;
import java.util.Set;

public class ClassUtils {
    private static final Set<String> default_package_set;

    /**
     * Class.isAssignableFrom() 的增强版本。 支持 null, 自动装箱,
     * 以及数字类型的隐私转换
     */
    public static boolean isAssignable(Class<?> lhs, Class<?> rhs) {
        if (lhs == null) return false;
        if (rhs == null) return (!(lhs.isPrimitive()));

        lhs = PrimitiveClassUtils.asBoxedClass(lhs, true);
        rhs = PrimitiveClassUtils.asBoxedClass(rhs, true);
        if (lhs.isAssignableFrom(rhs)) {
            return true;
        }

        lhs = PrimitiveClassUtils.asUnboxedClass(lhs);
        rhs = PrimitiveClassUtils.asUnboxedClass(rhs);
        if (lhs == null || rhs == null) {
            return false;
        }
        if (Integer.TYPE.equals(rhs)) {
            return ((Long.TYPE.equals(lhs)) || (Float.TYPE.equals(lhs)) || (Double.TYPE.equals(lhs)));
        }
        if (Long.TYPE.equals(rhs)) {
            return ((Float.TYPE.equals(lhs)) || (Double.TYPE.equals(lhs)));
        }
        if (Float.TYPE.equals(rhs)) {
            return Double.TYPE.equals(lhs);
        }
        if (Double.TYPE.equals(rhs)) {
            return false;
        }
        if (Boolean.TYPE.equals(rhs)) {
            return false;
        }
        if (Byte.TYPE.equals(rhs)) {
            return ((Short.TYPE.equals(lhs)) || (Integer.TYPE.equals(lhs)) || (Long.TYPE.equals(lhs)) || (Float.TYPE.equals(lhs)) || (Double.TYPE.equals(lhs)));
        }
        if (Short.TYPE.equals(rhs)) {
            return ((Integer.TYPE.equals(lhs)) || (Long.TYPE.equals(lhs)) || (Float.TYPE.equals(lhs)) || (Double.TYPE.equals(lhs)));
        }
        if (Character.TYPE.equals(rhs)) {
            return ((Integer.TYPE.equals(lhs)) || (Long.TYPE.equals(lhs)) || (Float.TYPE.equals(lhs)) || (Double.TYPE.equals(lhs)));
        }
        return false;
    }

    // 使用短名字
    public static String getShortClassName(Class<?> klass) {
        if (klass.isPrimitive()) {
            return klass.getName();
        } else {
            String pkg = klass.getPackage().getName();
            if (default_package_set.contains(pkg)) {
                if (klass.isMemberClass()) {
                    // 内部类
                    return klass.getEnclosingClass().getSimpleName() + "." + klass.getSimpleName();
                } else {
                    return klass.getSimpleName();
                }
            }
            return klass.getName();
        }
    }

    static {
        default_package_set = new HashSet<String>();
        default_package_set.add("java.lang");
        default_package_set.add("java.util");
        default_package_set.add("jetbrick.template.runtime");
    }
}
