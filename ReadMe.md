# Android利用Gradle多渠道打包如何动态替换资源，文字

## 动态替换logo
首先我们在app的build.gradle下面加入如下代码，以oem1为示例，


```gradle
productFlavors {
        oem1 {
            manifestPlaceholders = [
                    oemIcon : "@drawable/oem1",
            ]
        }
}
```
以上代码 `manifestPlaceholders` 主要是用于在AndroidManifest.xml内可以动态的替换oem图标，我们看`AndroidManifest.xml`里面是如何用到这oemIcon的

```xml
<application
        android:allowBackup="true"
        android:icon="${oemIcon}"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
```

以上代码主要是关注`android:icon="${oemIcon}"` ，这个其实在gradle打包后悔自动替换成我们在build.gradle里面设置的` ‘@drawable/oem1’`,这样就可以根据不同渠道包替换成不同的icon

## 动态替换不同oem的文字

首先在build.gradle里面配置我们需要的文字

```gradle
productFlavors {
        oem1 {
            resValue("string","dynamic_text","oem1的动态文字")
            manifestPlaceholders = [
                    oemIcon : "@drawable/oem1",
            ]
        }
}
```
**注意：我们的string.xml里面不允许有dynamic_text字段，有的话编译会报错.**

主要是resValue一行我们详细解释一下，
`string`表示的是我们要生成一个string字段,
`dynamic_text`指的是字段名称便于我们程序里面使用,
`"oem1的动态文字"`指的是我们的字符串的值

其实这一句想相当于我们在string.xml里面定义如下一行
```xml
<string name="dynamic_text">oem1的动态文字</string>
```

##动态替换资源文件
如果在程序中我们有一个地方需要根据不同的渠道对应不同的`drawable`，我们就可以用下列方法，下列方法是假设程序中有一处需要设置图片，思路是我们首先在`gradle`里面设置好值，在`AndroidManifest.xml`里面使用META_DATA，然后再从程序里面调用

build.gradle如下
```gradle
 productFlavors {
        oem1 {
            manifestPlaceholders = [
                    oemIcon : "@drawable/oem1",
            ]
        }
 }
```
AndroidManifest.xml如下
```xml
<meta-data
            android:name="OEM_LOGO"
            android:resource="${oemIcon}"
            />
```

java程序如下
MainActivity.java
```java

img.setImageResource(Util.getMetaDataInt(this,"OEM_LOGO"));
```
Util.java
```java
public class Util {

    public static int getMetaDataInt(Context context,String name) {
        try {
            ApplicationInfo appInfo = context.getApplicationContext().getPackageManager()
                    .getApplicationInfo(context.getApplicationContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            int logo = appInfo.metaData.getInt(name);
            return logo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}

```

主要思路就是利用META_DATA来货渠道资源的int值并且返回到程序中，达到动态生成的目的
