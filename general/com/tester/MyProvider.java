package com.tester;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.Provider;
import java.security.Security;
import java.util.Collection;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MyProvider extends Provider {
    
    private Provider delegate = Security.getProvider("");

    protected MyProvider(String name, String versionStr, String info) {
        super(name, versionStr, info);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Provider configure(String configArg) {
        return delegate.configure(configArg);
    }

    @Override
    public boolean isConfigured() {
        return delegate.isConfigured();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public double getVersion() {
        return delegate.getVersion();
    }

    @Override
    public String getVersionStr() {
        return delegate.getVersionStr();
    }

    @Override
    public String getInfo() {
        return delegate.getInfo();
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    @Override
    public synchronized void clear() {
        delegate.clear();
    }

    @Override
    public synchronized void load(InputStream inStream) throws IOException {
        delegate.load(inStream);
    }

    @Override
    public synchronized void putAll(Map<?, ?> t) {
        delegate.putAll(t);
    }

    @Override
    public synchronized Set<java.util.Map.Entry<Object, Object>> entrySet() {
        return delegate.entrySet();
    }

    @Override
    public Set<Object> keySet() {
        return delegate.keySet();
    }

    @Override
    public Collection<Object> values() {
        return delegate.values();
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        return delegate.put(key, value);
    }

    @Override
    public synchronized Object putIfAbsent(Object key, Object value) {
        return delegate.putIfAbsent(key, value);
    }

    @Override
    public synchronized Object remove(Object key) {
        return delegate.remove(key);
    }

    @Override
    public synchronized boolean remove(Object key, Object value) {
        return delegate.remove(key, value);
    }

    @Override
    public synchronized boolean replace(Object key, Object oldValue, Object newValue) {
        return delegate.replace(key, oldValue, newValue);
    }

    @Override
    public synchronized Object replace(Object key, Object value) {
        return delegate.replace(key, value);
    }

    @Override
    public synchronized void replaceAll(BiFunction<? super Object, ? super Object, ? extends Object> function) {
        delegate.replaceAll(function);
    }

    @Override
    public synchronized Object compute(Object key,
            BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
        return delegate.compute(key, remappingFunction);
    }

    @Override
    public synchronized Object computeIfAbsent(Object key, Function<? super Object, ? extends Object> mappingFunction) {
        return delegate.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public synchronized Object computeIfPresent(Object key,
            BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
        return delegate.computeIfPresent(key, remappingFunction);
    }

    @Override
    public synchronized Object merge(Object key, Object value,
            BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
        return delegate.merge(key, value, remappingFunction);
    }

    @Override
    public Object get(Object key) {
        return delegate.get(key);
    }

    @Override
    public synchronized Object getOrDefault(Object key, Object defaultValue) {
        return delegate.getOrDefault(key, defaultValue);
    }

    @Override
    public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
        delegate.forEach(action);
    }

    @Override
    public Enumeration<Object> keys() {
        return delegate.keys();
    }

    @Override
    public Enumeration<Object> elements() {
        return delegate.elements();
    }

    @Override
    public String getProperty(String key) {
        return delegate.getProperty(key);
    }

    @Override
    protected void putService(Service s) {
        delegate.putService(s);
    }

    @Override
    protected void removeService(Service s) {
        delegate.removeService(s);
    }

    @Override
    public synchronized Object setProperty(String key, String value) {
        return delegate.setProperty(key, value);
    }

    @Override
    public synchronized void load(Reader reader) throws IOException {
        delegate.load(reader);
    }

    @Override
    public void save(OutputStream out, String comments) {
        delegate.save(out, comments);
    }

    @Override
    public void store(Writer writer, String comments) throws IOException {
        delegate.store(writer, comments);
    }

    @Override
    public void store(OutputStream out, String comments) throws IOException {
        delegate.store(out, comments);
    }

    @Override
    public synchronized void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException {
        delegate.loadFromXML(in);
    }

    @Override
    public void storeToXML(OutputStream os, String comment) throws IOException {
        delegate.storeToXML(os, comment);
    }

    @Override
    public void storeToXML(OutputStream os, String comment, String encoding) throws IOException {
        delegate.storeToXML(os, comment, encoding);
    }

    @Override
    public void storeToXML(OutputStream os, String comment, Charset charset) throws IOException {
        delegate.storeToXML(os, comment, charset);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return delegate.getProperty(key, defaultValue);
    }

    @Override
    public Enumeration<?> propertyNames() {
        return delegate.propertyNames();
    }

    @Override
    public Set<String> stringPropertyNames() {
        return delegate.stringPropertyNames();
    }

    @Override
    public void list(PrintStream out) {
        delegate.list(out);
    }

    @Override
    public void list(PrintWriter out) {
        delegate.list(out);
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean contains(Object value) {
        return delegate.contains(value);
    }

    @Override
    public boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }

    @Override
    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    @Override
    public synchronized boolean equals(Object o) {
        return delegate.equals(o);
    }

    @Override
    public synchronized int hashCode() {
        return delegate.hashCode();
    }

    @Override
    protected void rehash() {
        delegate.rehash();
    }

    @Override
    public synchronized Object clone() {
        return delegate.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        delegate.finalize();
    }

    
    
    

}
