package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: HotSwapClassLoader </p>
 * <p>Description: 热加载工具类 </p>
 * <p>Date: 2020/7/14 12:53 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class HotSwapClassLoader extends URLClassLoader {

    public HotSwapClassLoader(URL[] urls) {
        super(urls);
    }

    public HotSwapClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public Class<?> load(String name)
            throws ClassNotFoundException {
        return load(name, false);
    }

    public Class<?> load(String name, boolean resolve)
            throws ClassNotFoundException {
        if (null != super.findLoadedClass(name)) {
            return reload(name, resolve);
        }

        Class<?> clazz = super.findClass(name);

        if (resolve) {
            super.resolveClass(clazz);
        }

        return clazz;
    }

    public Class<?> reload(String name, boolean resolve)
            throws ClassNotFoundException {
        return new HotSwapClassLoader(super.getURLs(), super.getParent()).load(
                name, resolve);
    }
}