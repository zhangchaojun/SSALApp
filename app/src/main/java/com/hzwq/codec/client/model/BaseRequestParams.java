package com.hzwq.codec.client.model;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基础请求参数(掌机项目特有的)
 * Created by xuzelei on 2016/3/23.
 */
public class BaseRequestParams implements Serializable {
    public final ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<>();
    public final ConcurrentHashMap<String, FileWrapper> fileParams = new ConcurrentHashMap<>();
    public final ConcurrentHashMap<String, List<FileWrapper>> fileArrayParams = new ConcurrentHashMap<>();

    /** 临时增加的属性，用来接口的MAC */
    public StringBuffer stringBuffer;

    public void put(String key, String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }

        createStringBuffer(key,value);
    }

    public void put(String key, int value) {
        if (key != null) {
            urlParams.put(key, String.valueOf(value));
        }

        createStringBuffer(key, String.valueOf(value));
    }

    public void put(String key, long value) {
        if (key != null) {
            urlParams.put(key, String.valueOf(value));
        }

        createStringBuffer(key, String.valueOf(value));
    }

    public boolean has(String key) {
        return urlParams.get(key) != null||
                fileParams.get(key) != null ||
                fileArrayParams.get(key) != null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }

        for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append("FILE");
        }

        for (ConcurrentHashMap.Entry<String, List<FileWrapper>> entry : fileArrayParams.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append("FILES(SIZE=").append(entry.getValue().size()).append(")");
        }
        return result.toString();
    }

    //----------------------------------文件------------------------------------------------
    /**
     * 文件包裹
     */
    public static class FileWrapper implements Serializable {
        public final File file;
        public final String contentType;
        public final String customFileName;

        public FileWrapper(File file, String contentType, String customFileName) {
            this.file = file;
            this.contentType = contentType;
            this.customFileName = customFileName;
        }
    }
    public void put(String key, File files[], String contentType, String customFileName) throws FileNotFoundException {
        if (key != null) {
            List<FileWrapper> fileWrappers = new ArrayList<FileWrapper>();
            for (File file : files) {
                if (file == null || !file.exists()) {
                    throw new FileNotFoundException();
                }
                fileWrappers.add(new FileWrapper(file, contentType, customFileName));
            }
            fileArrayParams.put(key, fileWrappers);
        }
    }
    public void put(String key, File files[]) throws FileNotFoundException {
        put(key, files, null, null);
    }

    public void put(String key, File file, String contentType, String customFileName) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }
        if (key != null) {
            fileParams.put(key, new FileWrapper(file, contentType, customFileName));
        }
    }
    public void put(String key, File file) throws FileNotFoundException {
        put(key, file, null, null);
    }
    public void put(String key, String customFileName, File file) throws FileNotFoundException {
        put(key, file, null, customFileName);
    }
    public void put(String key, File file, String contentType) throws FileNotFoundException {
        put(key, file, contentType, null);
    }

    private void createStringBuffer(String key , String value){
        if(stringBuffer==null){
            stringBuffer = new StringBuffer();
        }
        if(stringBuffer.length()>0) {
            stringBuffer.append("&");
        }
        stringBuffer.append(key).append("=").append(value);
    }

    /**
     * 根据参数生成 MultipartBody
     * @return
     */
    public MultipartBody.Builder createMultipartBody() {
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
        for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
            FileWrapper fileWrapper = entry.getValue();
            //不带参数的文件上传
            RequestBody body = RequestBody.create(StringUtils.isEmpty(fileWrapper.contentType) ? null : MediaType.parse(fileWrapper.contentType), fileWrapper.file);
            multipartBuilder.addPart(body);
            //带参数的文件上传
            //multipartBuilder.addFormDataPart(entry.getKey(), fileWrapper.customFileName, RequestBody.create(MediaType.parse(fileWrapper.contentType), fileWrapper.file));
        }

        for (ConcurrentHashMap.Entry<String, List<FileWrapper>> entry : fileArrayParams.entrySet()) {
            List<FileWrapper> fileWrapper = entry.getValue();
            for (FileWrapper fw : fileWrapper) {
                //不带参数的文件上传
                RequestBody body = RequestBody.create(StringUtils.isEmpty(fw.contentType) ? null : MediaType.parse(fw.contentType), fw.file);
                multipartBuilder.addPart(body);
                //带参数的文件上传
                //multipartBuilder.addFormDataPart(entry.getKey(), fw.customFileName, RequestBody.create(null, fw.file));
            }
        }

        //添加提交的参数
        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
        }

        return multipartBuilder;
    }
}
