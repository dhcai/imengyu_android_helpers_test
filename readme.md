# 简介

这是一个Android平台的工具箱模块，提供了很多原生功能模块，方便您最大程度上扩展Uniapp的原生功能。

这个是作者之前的一款App中使用的插件，现在分享给大家，可能有些地方不能满足您的要求，可以给我提建议, 我会尽量满足您的要求。

## 使用方法

插件分为多个模块，您可以导入您需要的模块，然后调用模块的方法即可。

例如，这里调用了图片裁剪模块，进行图片裁剪功能：

```js
const galleryModule = uni.requireNativePlugin('imengyu-AndroidToolbox-GalleryModule')

galleryModule.startCropImageActivity({
  sourceFilePath: path,
  aspectRatio: [ 1, 1 ],
}, (res) => {
  if(res.success) {
    console.log(res.tempFilePath);
  }
});

```

详细的模块API说明文档在下方。

## API

模块一览：

* 亮度模块 BrightnessModule
* 原生剪贴板模块 ClipboardModule
* 设备信息模块 DeviceModule
* 对话框模块 DialogModule
* 加密模块 EncryptModule
* FFmpeg视频处理模块 FFmpegModule
* 文件选择器模块 FilePickerModule
* 相册模块 GalleryModule
* 原生帮助模块 NativeHelperModule
* 底部虚拟按键模块 NavigationBarModule
* 网络检测工具模块 NetworkModule
* 原生通知模块 NotificationModule
* 路径模块 PathModule
* 权限模块 PermissionModule
* 屏幕模块 ScreenModule
* 分享扩展模块 ShareModule
* 状态栏模块 StatusBarModule
* 系统设置跳转工具模块 SystemSettingsModule
* 震动模块 VibrateModule
* 音量模块 VolumeModule

### 亮度模块 BrightnessModule

* `getBrightness()`

  获取当前屏幕的亮度。

  * **返回**

  | 类型 | 说明  |
  | ----  | ----  |
  | number  | 屏幕的亮度（0-255） |

* `setBrightness(brightness)`

  设置屏幕亮度（0-255）。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | brightness | number  | 屏幕的亮度（0-255）|

* `setAutoBrightnessEnabled(enabled)`

  设置屏幕是否开启自动亮度。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | enabled | boolean  | 是否开启自动亮度 |

* `isAutoBrightnessEnabled()`

  获取当前屏幕否开启自动亮度。

  * **返回**

  | 类型 | 说明  |
  | ----  | ----  |
  | boolean  | 是否开启自动亮度 |

* `getWindowBrightness()`

  获取当前应用窗口屏幕的亮度。

  * **返回**

  | 类型 | 说明  |
  | ----  | ----  |
  | number  | 当前应用窗口屏幕的亮度（0-255） |

* `setWindowBrightness(brightness)`

  设置当前应用窗口屏幕亮度（0-255）。此方法仅仅更改当前应用窗口，不会改变系统的屏幕亮度。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | brightness | number  | 亮度（0-255）|

### 原生剪贴板模块 ClipboardModule

* `copyContentToClipboard(content)`

  复制文本至剪贴板中。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | content | string  | 要复制的文本 |

* `copyFileToClipboard(path)`

  复制文件至剪贴板中。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | path | string  | 要复制的文件路径，必须是绝对路径。如果是uniapp资源路径，需要使用 `plus.io.convertAbsoluteFileSystem` 转换 |

### 设备信息模块 DeviceModule

* `isAdbEnabled()` 获取当前设备是否开启Adb调试。
* `isDeviceRooted()` 获取设备是否Root。
* `isEmulator()` 获取设备是否是模拟器。
* `isDevelopmentSettingsEnabled()` 获取设备是否开启了开发者模式。
* `isTablet()` 获取设备是否是平板。
* `getAndroidID()` 获取AndroidID。
* `getManufacturer()` 获取设备制造商。
* `getModel()` 获取设备模型。
* `getUniqueDeviceId()` 获取设备唯一ID。
* `getUniqueDeviceIdWithCache()` 获取设备唯一ID(带缓存)。
* `getUniqueDeviceIdWithPrefix(prefix)`

  获取设备唯一ID（加前缀）。

  * **参数**

  | 名称 | 类型 | 说明  |
  | ---- | ----  | ----  |
  | prefix | string  | 前缀 |

  * **返回**

  | 类型 | 说明  |
  | ----  | ----  |
  | string  | - |
  
* `getMacAddress()` 获取设备MAC地址。
* `getSDKVersionName()` 获取SDK版本名称。
* `getSDKVersionCode()` 获取SDK版本号。

### 对话框模块 DialogModule
  
* `setTheme(options)`

  设置原生对话框的主题。推荐在App启动时设置，动态设置可能会出现问题。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | dialogStyle | `'Material'|'Kongzue'|'IOS'|'MIUI'`  | 对话框样式 |
  | dialogTheme | `'LIGHT'|'DARK'` | 对话框主题 |

* `showBottomMenu(options, callback)`

  显示原生底部菜单。(与 `uni.showActionSheet` 相似)

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | title | string  | 对话框标题 |
  | choices | string[] | 条目文字 |
  | showCancel | boolean | 是否显示取消按钮，可不填，默认是 |
  | dialogStyle | `'Material'|'Kongzue'|'IOS'|'MIUI'` | 对话框样式，可不填 |
  | dialogTheme | `'LIGHT'|'DARK'` | 对话框主题，可不填 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | chooseIndex | number | 选中的条目索引 |
  | chooseText | string | 选中的条目文字 |
  
* `showLoading(options, callback)`

  显示原生加载中对话框。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | title | string  | 对话框标题 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | dialogId | number | 当前对话框ID，可以使用此ID进行后续操作 |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |
  
* `hideLoading(options)`

  隐藏原生加载中对话框。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | id | number  | `showLoading` 返回的 `dialogId` |
  
* `setLoadingProgress(options)`

  更新原生加载中对话框的百分比。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | id | number  | `showLoading` 返回的 `dialogId` |
  | progress | number  | 百分比，0-1 |
  
* `showTipDialog(options, callback)`

  显示原生提示对话框。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | title | string  | 对话框标题 |
  | type | `'success'|'warn'|'error'|'none'`  | 提示类型 |
  | duration | number  | 显示时长，毫秒 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | dialogId | number | 当前对话框ID，可以使用此ID进行后续操作 |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |
  
* `hideTipDialog(options)`

  隐藏原生提示对话框。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | id | number  | `showTipDialog` 返回的 `dialogId` |
  
* `showToast(options)`

  显示原生土司。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | title | string  | 对话框标题 |

* `showSystemToast(options)`

  显示原生土司 (系统样式)。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | title | string  | 对话框标题 |

* `showInputDialog(options, callback)`

  显示原生输入对话框。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | title | string  | 对话框标题 |
  | message | string  | 对话框文字 |
  | okText | string  | 确定按钮文字，默认“确定” |
  | cancelText | string  | 取消按钮文字，默认“取消” |
  | hintText | string  | 输入框提示文字 |
  | initialText | string  | 输入框开始时的文字 |
  | dialogStyle | `'Material'|'Kongzue'|'IOS'|'MIUI'` | 对话框样式 |
  | dialogTheme | `'LIGHT'|'DARK'` | 对话框主题 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | confirm | boolean | 是选中确定按钮还是取消按钮 |
  | inputStr | string | 文本框输入的文字 |
  
* `showMessageDialog(options, callback)`

  显示原生信息对话框。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | title | string  | 对话框标题 |
  | message | string  | 对话框文字 |
  | okText | string  | 确定按钮文字，默认“确定” |
  | cancelText | string  | 取消按钮文字，默认“取消”，为空时不显示 |
  | thirdText | string  | 第三按钮文字，默认“”，为空时不显示 |
  | dialogStyle | `'Material'|'Kongzue'|'IOS'|'MIUI'` | 对话框样式 |
  | dialogTheme | `'LIGHT'|'DARK'` | 对话框主题 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | clicked | `'ok'|'cancel'|'other'` | 返回用户选中的按钮，ok确定按钮；cancel取消按钮；other，第三按钮 |
  
* `showShareDialog(options, callback)`

  显示原生分享对话框。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | choices | string[] | 显示在列表中的分享类型。可选分享类型 wechat-moments，qq，wechart，sina，link，poster，weapp-qrcode，qrcode |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | type | string | 返回用户选中的按钮名称 |
  | index | number | 返回用户选中的按钮索引 |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |

* `showPopTip(options, callback)`

  显示原生顶部 PopTip。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | title | string  | 对话框标题 |
  | icon | `''|'success'|'error'|'warning'|'build'|'check'|'bug_report'` | 图标，为空时不显示  |
  | buttonText | string  | 按钮文字，为空则不显示按钮 |
  | duration | number  | 显示时间，毫秒 |
  | dialogStyle | `'Material'|'Kongzue'|'IOS'|'MIUI'` | 对话框样式 |
  | dialogTheme | `'LIGHT'|'DARK'` | 对话框主题 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | type | string | 返回用户选中的按钮名称 |
  | index | number | 返回用户选中的按钮索引 |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |

* `showConfirmDialog(options, callback)`

  显示原生确认对话框。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | title | string  | 对话框标题 |
  | message | string  | 对话框文字 |
  | okText | string  | 确定按钮文字，默认“确定” |
  | cancelText | string  | 取消按钮文字，默认“取消”，为空时不显示 |
  | cancelable | boolean  | 是否可以点击外围区域关闭对话框 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | confirm | boolean | 是否点击了确定，否则为取消 |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |

### 加密模块 EncryptModule

* `MD5(content)`

  对字符串进行MD5编码。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | content | string  | 字符串 |

  * **返回**

  | 类型 | 说明  |
  | ----  | ----  |
  | string  | 返回MD5结果 |

* `SHA1(content)`

  对字符串进行SHA1编码。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | content | string  | 字符串 |

  * **返回**

  | 类型 | 说明  |
  | ----  | ----  |
  | string  | 返回SHA1结果 |
  
* `SHA256(content, key)`

  对字符串进行SHA256加密。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | content | string  | 字符串 |
  | key | string  | 密钥 |

  * **返回**

  | 类型 | 说明  |
  | ----  | ----  |
  | string  | 返回结果 |
  
* `SHA512(content, key)`

  对字符串进行SHA512加密。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | content | string  | 字符串 |
  | key | string  | 密钥 |

  * **返回**

  | 类型 | 说明  |
  | ----  | ----  |
  | string  | 返回结果 |
  
* `MD5File(content)`

  对文件进行MD5运算。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | filePath | string  | 文件路径，必须是绝对路径。 |

  * **返回**

  | 类型 | 说明  |
  | ----  | ----  |
  | string  | 返回MD5结果 |

### FFmpeg视频处理模块 FFmpegModule

* `runCmd(options, callback)`

  执行 FFmpeg 命令

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | command | string  | 一条 ffmpeg 命令，会自动按空格分割 |
  | commands | string[] | 一组 ffmpeg 命令，按空格，commands 和 command 二选一。 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |
  | result | number | 如果成功，则返回FFmpeg执行的返回值 |

* `videoToImage(options, callback)`

  视频解码成一帧帧图片

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | src | string  | 视频源路径 |
  | dir | string  | 图片输出目录 |
  | startTime | number  | 解码图片开始时间，单位s，默认0 |
  | count | number  | 解码数量，默认1 |
  | tag | number  | 标志位，默认0 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |
  | tag | number | 输入的标志位 |
  | src | string | 输入的视频源路径 |
  | dest | string | 图片输出路径 |

* `decodeToImageWithCall(options, callback)`

  异步任务模式，将视频解码成一帧帧图片。

  每完成一次图片的解码与保存会回调一次，并传回新保存图片的地址与当前时间图片下标。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | src | string  | 视频源路径 |
  | dir | string  | 图片输出目录 |
  | startTime | number  | 解码图片开始时间，单位s，默认0 |
  | count | number  | 解码数量，默认1 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |
  | src | string | 输入的视频源路径 |
  | dest | string | 图片输出目录 (解码完成的图片放在 dir 目录下，按 temp秒.jpg 名字排放) |
  | path | string | 当前帧图片图片输出目录 |
  | index | number | 当前帧图片索引 |

* `decodeToImageCall(options, callback)`

  执行指定帧视频帧图片任务。通常配合 decodeToImageWithCall 一起使用。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | src | string  | 视频源路径 |
  | index | number | 解码的秒数。 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |

* `clipVideo(options, callback)`

  裁剪视频。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | src | string  | 视频源路径 |
  | dist | string  | 视频输出绝对路径 |
  | startTime | number  | 裁剪开始时间，单位ms |
  | duration | number  | 裁剪时长，单位ms |
  | tag | number  | 标志位，默认0 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |

* `compressVideo(options, callback)`

  压缩视频。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | src | string  | 视频源路径 |
  | dist | string  | 压缩后的临时视频输出绝对路径 |
  | tag | number  | 标志位，默认0 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |
  | src | string  | 视频源路径 |
  | dist | string  | 压缩后的临时视频输出绝对路径 |
  | tag | number  | 标志位 |

### 文件选择器模块 FilePickerModule

* `pickFileWithSystem(options, callback)`

  调用系统选择器选择本地文件。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | type | string  | 设置可选文件类型。文件类型可以是，`image/*`：选择图片； `audio/*` 选择音频； `video/*` 选择视频； `video/*;image/*` 同时选择视频和图片；`*/*` 无限制 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | path | string | 选择的文件路径 |

* `pickFiles(options, callback)`

  调用选择器选择本地文件。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | maxCount | number  | 可选数量 |
  | docType | boolean | 如果为 true，则忽略types，设置选择器只选择文档类型，包括ppt，word，excel等等 |
  | type | string[] | 自定义类型，设置可以选择的文件类型，写后缀名，如： [ "xls","xlt","xlsx","xltx" ]。 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |
  | paths | string[] | 选择的文件路径数组 |
  | files | File[] | 选择的文件信息数组 |

  * **File 结构说明**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | name | string | 名称 |
  | path | string | 路径 |
  | mimeType | string | 文件的mime类型 |
  | size | string | 大小 |
  | date | string | 修改日期 |

* `openFileWithApp(options)`

  调用系统选择App打开本地文件

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | file | string  | 文件路径，必须是本地绝对路径 |
  | title | string | 对话框标题，默认是 “选择应用打开此文件” |

### 相册模块 GalleryModule

* `getSystemAlbums(options, callback)`

  获取系统相册数据【异步】。此方法可以获取系统相册数据，方便您制作自己的图片选择器。

  * **Options 参数**

  无。

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | list | GalleryInfo[] | 相册列表 |

  * **GalleryInfo 结构信息**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | name | string | 相册列表 |
  | folderPath |string | 相册路径 |
  | coverImagePath | string | 相册封面图片的路径 |
  | photos | PhotoInfo[] | 相册图片列表 |

  * **PhotoInfo 结构信息**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | name | string | 图片名称 |
  | path | string | 图片全路径 |
  | type | string | 图片类型 |
  | width | number | 图片宽度 |
  | height | number | 图片高度 |
  | orientation | number | 图片旋转角度 |
  | size | number | 图片文件大小，单位：Bytes |
  | duration | number | 视频时长，单位：毫秒（仅视频条目） |
  | time | number | 时间戳 |

* `getSystemVideos(options, callback)`

  获取系统相册视频数据 【异步】。此方法可以获取系统相册数据，方便您制作自己的视频选择器。

  * **Options 参数**

  无。

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | videos | VideoInfo[] | 视频数据 |

  * **VideoInfo 结构信息**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | name | string | 视频名称 |
  | path |string | 视频全路径 |
  | size | number | 视频文件大小，单位：Bytes |
  | duration | number | 视频时长，单位：毫秒 |

* `getVideoThumbnail(options, callback)`

  获取本地视频缩略图 【异步】。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | path | string  | 视频的路径 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | videoThumbnail | string | 视频的缩略图临时文件 |

* `getImageExifOrientation(path)`

  获取本地图片的Exif旋转信息。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | path | string  | 本地文件路径 |

  * **返回**

  | 类型 |说明  |
  |  ----  | ----  |
  | number | 返回旋转度数 |

* `getVideoSize(options, callback)`

  获取本地视频宽高 【异步】。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | path | string  | 视频的路径 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | width | number | 图片宽度 |
  | height | number | 图片高度 |
  | size | number | 文件大小，单位：Bytes |
  | rotation | number | 视频的方向角度 |

* `startVideoEditActivity(options, callback)`

  开启视频剪裁窗口。（与微信裁剪视频工具类似）

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | path | string  | 视频的路径 |
  | title | string  | 剪裁窗口顶部显示的文字 |
  | maxDuration | number  | 最大可剪裁时长，单位秒，默认60秒 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | success | boolean | 是否成功 |
  | tempVideoPath | string | 剪裁完成后的临时视频的路径。使用完之后请使用 `deleteTempVideo` 删除 |

* `deleteTempVideo(options, callback)`

  删除 `startVideoEditActivity` 剪裁的临时视频 【异步】。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | path | string  | 临时视频的路径 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |

* `startCropImageActivity(options, callback)`

  打开裁剪图片窗口 (基于UCorp)。

  * **Options 参数**

  ```ts
  {
    sourceFilePath: string, //源文件路径
    useSourceImageAspectRatio?: boolean, //是否使用源图像比例剪裁
    aspectRatio?: number[], //是否使用指定比例剪裁，当useSourceImageAspectRatio为true时此值无效
    maxResultSize?: number[], //最大可以允许的图片的长宽
    activeControlsWidgetColor?: string, //控件激活状态颜色
    cropFrameColor?: string, //剪裁框颜色
    cropGridColor?: string, //剪裁框网格颜色
    dimmedLayerColor?: string, //裁剪边界周围暗显区域的所需颜色
    logoColor?: string, //徽标填充所需的解析颜色（默认为深灰色）
    rootViewBackgroundColor?: string, //根视图的背景色
    statusBarColor?: string, //状态栏颜色
    toolbarColor?: string, //工具栏颜色
    toolbarWidgetColor?: string, //工具栏文本和按钮所需的解析颜色（默认为深橙色）
    toolbarTitle?: string, //工具栏标题
    compressionQuality?: number, //[0 - 100] 剪裁图片压缩质量，png图片无此参数
    compressionFormat?: 'jpeg'|'png'|'webp', //设置压缩图片格式
    circleDimmedLayer?: boolean, //设置是否是圆形剪裁框
    showCropFrame?: boolean, //设置是否显示剪裁框
    showCropGrid?: boolean, //设置是否显示剪裁框网格
    freeStyleCropEnabled?: boolean, //设置是否可以自由调整剪裁框
    hideBottomControls?: boolean, //设置是否显隐藏底部控件
    maxScaleMultiplier?: integer, //此方法设置用于从最小图像比例计算最大图像比例的乘数。
    maxBitmapSize?: integer, //用于设置从输入Uri解码并在视图中使用的位图宽度和高度的最大大小。
    imageToCropBoundsAnimDuration?: integer, //此方法设置图像的动画持续时间，以包裹裁剪边界
    cropFrameStrokeWidth?: integer, //裁剪帧线的宽度（以像素为单位）
    cropGridRowCount?: integer, //裁剪网格的行数。
    cropGridColumnCount?: integer, //裁剪网格的列数。
  }
  ```

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |
  | tempFilePath | string  | 剪裁后的临时图片路径 |

* `chooseImage(options, callback)`

  选择图片。

  * **Options 参数**

  ```ts
  {
    sourceType: [ 'album', 'camera' ], //同 uni.chooseImage sourceType
    count: number, //可选最大数量
    hasGif: boolean, //是否显示动图，默认true
    hasVideo: boolean, //是否显示视频，默认false
    needSize: boolean, //是否需要获取图像宽高，默认false,
    compressQuality: number, //压缩图片的质量，取值范围为1-100，数值越小，质量越低。默认值为80。
    compress: { //如果指定此参数，返回的图片均会进行强制压缩，并且调整到您设置的大小.
      //如果指定了长宽其中某个值，则会按比例缩小图片。
      //如果指定了长宽其两个值，则会按您设置的长宽直接拉伸图片。
      width: Number, //目标压缩宽度，单位为px，用于计算裁剪宽高比。
      height: Number, //目标压缩高度，单位为px，用于计算裁剪宽高比。
    },
    imageDirectionCorrection: number, //纠正图片的旋转方向，默认旋转方向是依照照片的拍摄方向，你可以通过设置这个值，让图片在默认旋转方向上再旋转多少度。
  }
  ```

  * **Callback 返回**

  ```ts
  {
    success: boolean,
    errMsg: string,
    isOriginal: boolean,  //是否选中了原图
    tempFilePaths: string[], //选中图像信息路径数组
    tempFiles: {
      name: string, //文件名
      width: number, //宽，必须指定needSize为true才有效
      height: number, //高，必须指定needSize为true才有效
      type: string, //图片类型
      duration: number, //视频的时长
      orientation: number, //图片的旋转方向
      time: number, //图片创建时间戳
      path: string //图片完整路径
    }[], //选中图像信息
  }
  ```

* `chooseVideo(options, callback)`

  选择本地视频。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | title | string  | 对话框标题 |
  | showCameraButton | boolean  | 是否显示相机按钮, 默认显示 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |
  | chooseVideoPath | string  | 选中的视频路径，同 chooseVideo.path，如果选中的是相机按钮，则为 "camera"(您可以调用uniapp的api进行拍摄录像，本库暂未提供录像功能) |
  | chooseVideo | VideoInfo | 选中的视频信息 |

  * **VideoInfo 结构信息**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | name | string | 视频名称 |
  | path |string | 视频全路径 |
  | size | number | 视频文件大小，单位：Bytes |
  | duration | number | 视频时长，单位：毫秒 |

* `compressImage(options, callback)`

  压缩图片。

  如果指定了长宽其中某个值，则会按比例为你缩小图片。
  如果指定了长宽其两个值，则会按您设置的长宽直接拉伸图片。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | path | string  | 源文件路径 |
  | width | number  | 压缩宽度 |
  | height | number  | 压缩高度 |
  | quality | number  | 压缩质量，默认80 |
  | directionCorrection | number  | 图片额外旋转度数，默认0° |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |
  | path | string  | 压缩后的临时文件路径 |

### 原生帮助模块 NativeHelperModule

* `installApk(filePath)`

  安装 Apk 文件。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | filePath | string  | Apk 文件路径，必须是绝对路径 |

* `execShell(shell, callback)`

  执行Shell命令。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | shell | string  | Shell命令 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | result | int | 返回值 |
  | errorMsg | string | 错误信息 |
  | successMsg | string | 成功信息 |

* `execRootShell(shell, callback)`

  执行Root Shell命令，设备必须Root才能成功执行。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | shell | string  | Shell命令 |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | result | int | 返回值 |
  | errorMsg | string | 错误信息 |
  | successMsg | string | 成功信息 |

* `getAppUid()`

  获取App的UID。

  * **返回**

  | 类型 |说明  |
  | ----  | ----  |
  | number | UID |

* `isAppInstalled(pkgName)`

  获取指定App是否安装。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | pkgName | string  | 要查询的包名 |

  * **返回**

  |  类型 |说明  |
  | ----  | ----  |
  | boolean | 是否安装 |

* `isAppRunning(pkgName)`

  获取指定App是否在运行。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | pkgName | string  | 要查询的包名 |

  * **返回**

  |  类型 |说明  |
  | ----  | ----  |
  | boolean | 否在运行 |

* `isFirstTimeInstall()` 获取App是否是第一次安装到此设备。
* `isFirstTimeInstalled()` 获取App是否是第一次安装。

* `launchApp(pkgName)`

  启动指定App。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | pkgName | string  | 要启动的包名 |

* `exitApp()`

  退出App。

* `getAppCacheSize()`

  获取APP缓存目录大小。

  * **返回**

  |  类型 |说明  |
  | ----  | ----  |
  | string | 大小，已转换为可读的字符串 |

* `clearAppCache(callback)`

  清空APP缓存目录。

* `testCrash()`

  测试APP崩溃。

### 底部虚拟按键模块 NavigationBarModule

* `hideNavigationBar()` 隐藏底部导航条。
* `showNavigationBar()` 显示底部导航条。
* `setTransparentNavigationBar()` 设置底部导航条为透明色。
* `setBlackNavigationBar()` 设置底部导航条为黑色。
* `resetBlackNavigationBar()` 重置底部导航条状态（重置为白色或者是默认状态）。
* `getBlackNavigationBarHeight()`

  获取底部导航条的高度（像素）。

  * **返回**

  | 类型 |说明  |
  | ----  | ----  |
  | number | 底部导航条的高度（像素） |

* `getNavigationBarIsLightMode()` 获取底部导航条是否在亮色模式。
* `getNavigationBarVisible()` 获取底部导航条是否显示。

### 网络检测工具模块 NetworkModule

* `isConnected()` 获取网络是否连接。
* `is4G()` 获取是否是4G网络。
* `is5G()` 获取是否是5G网络。
* `isAvailable()` 获取手机是否有网络功能。
* `isAvailableByPing(ip)`

  使用PING获取网络是否可用。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | ip | string  | 指定要ping的默认地址 |

  * **返回**

  | 类型 |说明  |
  | ----  | ----  |
  | boolean | - |

* `isAvailableByPing()` 使用PING获取网络是否可用。
* `isAvailableByDns(dns)`

  使用Dns查询获取网络是否可用。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | dns | string  | 指定要查询的DNS记录 |

  * **返回**

  | 类型 |说明  |
  | ----  | ----  |
  | boolean | - |

* `isAvailableByDns()` 使用Dns查询获取网络是否可用。
* `isBehindProxy()` 检查网络是否存在代理。
* `isMobileData()` 检查是否是移动数据网络。
* `isUsingVPN()` 检查是否是使用了VPN。
* `isWifiAvailable()` 检查WIFI是否可用。
* `isWifiConnected()` 检查WIFI是否连接。
* `getGatewayByWifi()`

  获取Wifi的网关地址。

  * **返回**

  | 类型 |说明  |
  | ----  | ----  |
  | string | - |

* `getIpAddressByWifi()`

  获取Wifi的当前IP地址。

  * **返回**

  | 类型 |说明  |
  | ----  | ----  |
  | string | - |

* `getIPAddress(ipv4)`

  获取当前IP地址。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | ipv4 | boolean  | 是否获取IPV4地址 |

  * **返回**

  | 类型 |说明  |
  | ----  | ----  |
  | v | - |

* `getBroadcastIpAddress()`

  获取当前网络的广播地址。

  * **返回**

  | 类型 |说明  |
  | ----  | ----  |
  | string | - |

* `getSSID()`

  获取已连接Wifi的SSID。

  * **返回**

  | 类型 |说明  |
  | ----  | ----  |
  | String | - |

* `getWifiEnabled()` 获取Wifi是否启用。
* `getNetworkType()`

  获取网络类型。

  * **返回**

  | 类型 |说明  |
  | ----  | ----  |
  | string | 有以下几种返回： |
  | ETHERNET |有线网络连接|
  | WIFI |WIFI连接|
  |5G |5G网络连接|
  | 4G |4G网络连接|
  |3G |3G网络连接|
  |2G |2G网络连接|
  |UNKNOWN |未知连接|
  | NO |无网络|

### 原生通知模块 NotificationModule

* `areNotificationsEnabled()`

  获取当前App是否开启了通知。

  * **返回**

  | 类型 |说明  |
  | ----  | ----  |
  | boolean | - |

* `cancel(id)`

  取消某个ID的通知。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | id | number  | ID |

* `cancelWithTag(tag, id)`

  取消某个Tag的通知。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | tag | string  | tag |
  | id | number  | ID |

* `cancelAll()`

  取消所有通知。

* `notify(options)`

  发送原生通知。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | id | number  | ID |
  | channelId | number  | 频道ID |
  | tag | string  | 标签 |
  | color | string  | 颜色 |
  | text | string  | 说明文字 |
  | title | string  | 标题文字 |
  | smallIcon | number  | 小图标ID |
  | largeIcon | string  | 大图标路径，本地文件 |
  | category | string  | 分类，可选 call navigation msg email event promo alarm progress social err transport sys service recommendation status reminder car_emergency car_warning car_information |
  | autoCancel | boolean  | 自动消失 |
  | onlyAlertOnce | boolean  | 只提醒一次 |
  | ongoing | string  | Set whether this is an ongoing notification. Ongoing notifications differ from regular notifications in the following ways: Ongoing notifications are sorted above the regular notifications in the notification panel. Ongoing notifications do not have an 'X' close button, and are not affected by the "Clear all" button. |
  | number | number  | Set the large number at the right-hand side of the notification. This is equivalent to setContentInfo, although it might show the number in a different font size for readability. |
  | priority | number  | 优先级 |
  | vibrate | number[]  | 震动频率，ms |

  * **返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | success | boolean | 是否成功 |
  | errMsg | string | 错误信息 |

### 路径模块 PathModule

* `getDataPath()` 获取 App 的 Data 目录
* `getExternalAppDataPath()` 获取 App 的外置 Data 目录
* `getRootPath()` 获取根目录 /system
* `getInternalAppDataPath()` 获取 App 的内置 Data 目录
* `getDownloadCachePath()` 获取App下载缓存文件夹路径
* `getInternalAppCodeCacheDir()`
* `getInternalAppCachePath()`
* `getInternalAppDbsPath()`
* `getInternalAppFilesPath()` 获取App内置文件夹路径
* `getExternalMusicPath()` 获取外置音乐文件夹路径
* `getExternalPodcastsPath()`
* `getExternalStoragePath()` 获取外置存储路径
* `getExternalRingtonesPath()` 获取外置铃声文件夹路径
* `getExternalAlarmsPath()` 获取外置闹钟文件夹路径
* `getExternalNotificationsPath()`
* `getExternalPicturesPath()` 获取外置图片文件夹路径
* `getExternalMoviesPath()` 获取外置电影文件夹路径
* `getExternalDownloadsPath()` 获取外置下载文件夹路径
* `getExternalDcimPath()` 获取外置相机文件夹路径
* `getExternalDocumentsPath()` 获取外置文档文件夹路径
* `getExternalAppFilesPath()` 获取App外置文件夹路径
* `getExternalAppCachePath()` 获取App外置缓存文件夹路径
* `getExternalAppMusicPath()` 获取App外置音乐文件夹路径
* `getExternalAppPodcastsPath()` 获取App外置Podcasts文件夹路径
* `getExternalAppRingtonesPath()` 获取App外置铃声文件夹路径
* `getExternalAppAlarmsPath()` 获取App外置闹钟文件夹路径
* `getExternalAppNotificationsPath()` 获取App外置通知文件夹路径
* `getExternalAppPicturesPath()` 获取App外置图片文件夹路径
* `getExternalAppMoviesPath()` 获取App外置电影文件夹路径
* `getExternalAppDownloadPath()` 获取App外置下载文件夹路径
* `getExternalAppDcimPath()` 获取App外置相册文件夹路径
* `getExternalAppDocumentsPath()` 获取App外置文档文件夹路径
* `getExternalAppObbPath()`
* `getAppDataPathExternalFirst()`
* `getFilesPathExternalFirst()`
* `getCachePathExternalFirst()`

### 权限模块 PermissionModule

* **判断权限**
* `isExternalStorageManager()`

  获取当前App是否有存储管理权限（Android11+），Android11以下永远返回true。

* `isAndroid11AndUp()`

  获取当前是否是 Android11+ 设备。

* `isGrantedPermission(name)`

  获取当前App是否授予某个权限。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | name | string  | 权限的完整名称，例如 “android.permission.SYSTEM_ALERT_WINDOW” |

* `isGrantedDrawOverlays()` 获取当前App是否授予悬浮窗权限。
* `isGrantedWriteSettings()` 获取当前App是否授予写入系统设置权限。
* `isGrantedSystemAlertWindow()` 获取当前App是否授予全局窗口权限 。
* `isGrantedInstallPackages()` 获取当前App是否授予安装App权限 。
* `isGrantedWriteExternalStorage()` 获取当前App是否授予写入外置存储权限 。
* `isGrantedReadExternalStorage()` 获取当前App是否授予读取外置存储权限 。
* `isGrantedManageExternalStorage()` 获取当前App是否授予管理外置存储权限（Android11+） 。
* `isGrantedReadCalender()` 获取当前App是否授予读取日历权限 。
* `isGrantedWriteCalender()` 获取当前App是否授予写入日历权限 。
* `isGrantedCamera()` 获取当前App是否授予摄像机权限 。
* `isGrantedReadContacts()` 获取当前App是否授予读取联系人权限 。
* `isGrantedWriteContacts()` 获取当前App是否授予写入联系人权限 。
* `isGrantedGetAccounts()` 获取当前App是否授予获取手机账户权限 。
* `isGrantedAccessFineLocation()` 获取当前App是否授予读取精确位置权限 。
* `isGrantedAccessCoarseLocation()` 获取当前App是否授予读取粗略位置权限 。
* `isGrantedAccessMediaLocation()` 获取当前App是否授予权限 Manifest.permission.ACCESS_MEDIA_LOCATION 。
* `isGrantedAccessBackgroundLocation()` 获取当前App是否授予权限 Manifest.permission.ACCESS_BACKGROUND_LOCATION 。
* `isGrantedReadPhoneState()` 获取当前App是否授予读取手机信息权限 。
* `isGrantedCallPhone()` 获取当前App是否授予拨打电话权限 。
* `isGrantedReadCallLog()` 获取当前App是否授予读取通话记录权限 。
* `isGrantedWriteCallLog()` 获取当前App是否授予写入通话记录权限 。
* `isGrantedAddVoiceMail()` 获取当前App是否授予添加声音邮件权限 。
* `isGrantedUseSIP()` 获取当前App是否授予使用SIP权限 。
* `isGrantedBodySensors()` 获取当前App是否授予传感器权限 。
* `isGrantedSendSms()` 获取当前App是否授予发送短信权限 。
* `isGrantedReceiveSms()` 获取当前App是否授予接受短信权限 。
* `isGrantedReadSms()` 获取当前App是否授予读取短信权限 。
* `isGrantedReceiveWapPush()` 获取当前App是否授予权限 。
* `isGrantedReceiveMms()` 获取当前App是否授予权限 。
* `isGrantedRecordAudio()` 获取当前App是否授予录音权限 。
* **请求权限**

* `request(permissionName, callback)`

  请求自定义权限 。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | permissionName | string  | 权限的完整名称，例如 “android.permission.SYSTEM_ALERT_WINDOW” |

  * **Callback 返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | granted | boolean | 是否授予权限 |
  | permissions | string[] | 当前请求的权限名称列表 |
  | all | boolean  | 用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个 |
  | never | boolean  | 用户是否点击取消并且永久拒绝 |

* 请求权限返回数据与上相同，下方相关API不再赘述。
* `requestManageExternalStorage(callback)` 请求管理外置存储权限（Android11+） 。
* `requestWriteExternalStorage(callback)` 请求写入外置存储权限 。
* `requestReadExternalStorage(callback)` 请求读取外置存储权限 。
* `requestRecordAudio(callback)` 请求录音权限 。
* `requestWriteSettings(callback)` 请求写入系统设置权限 。
* `requestAccessFineLocation(callback)` 请求精确定位权限 。
* `requestAccessCoarseLocation(callback)` 请求获取粗略位置权限权限 。
* `requestAccessBackgroundLocation(callback)` 请求ACCESS_BACKGROUND_LOCATION权限 。
* `requestAccessMediaLocation(callback)` 请求ACCESS_MEDIA_LOCATION权限 。
* `requestReadCalender(callback)` 请求读取日历权限 。
* `requestCamera(callback)` 请求相机权限 。
* `requestWriteContacts(callback)` 请求写入通讯录权限 。
* `requestReadContacts(callback)` 请求读取通讯录权限 。
* `requestGetAccounts(callback)` 请求获取手机账号权限 。
* `requestReadPhoneState(callback)` 请求读取手机状态权限 。
* `requestCallPhone(callback)` 请求拨打电话权限 。
* `requestReadCallLog(callback)` 请求读取通话记录权限 。
* `requestWriteCallLog(callback)` 请求写入通话记录权限 。
* `requestAddVoiceMail(callback)` 请求权ADD_VOICEMAIL限 。
* `requestUseSIP(callback)` 请求USE_SIP权限 。
* `requestBodySensors(callback)` 请求身体传感器权限 。
* `requestSendSms(callback)` 请求发送短信权限 。
* `requestReceiveSms(callback)` 请求接收短信权限 。
* `requestReadSms(callback)` 请求读取短信权限 。
* `requestReceiveWapPush(callback)` 请求RECEIVE_WAP_PUSH权限 。
* `requestReceiveMms(callback)` 请求接收MMS权限 。
* `requestGroupBlueTooth(callback)` 请求蓝牙组权限 。
* `requestGroupCalendar(callback)` 请求日历组权限 。
* `requestGroupContacts(callback)` 请求通讯录组权限 。
* `requestGroupSensors(callback)` 请求传感器组权限 。
* `requestGroupStorage(callback)` 请求存储组权限 。

### 屏幕模块 ScreenModule

* `setFullScreen(fullScreen)`

  设置页面是否全屏。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | fullScreen | boolean  | 是否全屏 |

* `setSecure(enable)`

  设置页面是否禁止截屏录屏。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | enable | boolean  | 是否禁止 |

* `setFullScreen(keepScreenOn)`

  设置阻止休眠。

  * **参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | keepScreenOn | boolean  | 是否阻止休眠 |

* `toggleFullScreen()` 切换页面是否全屏状态。

* `getKeepScreenOn()` 获取当前是否设置阻止休眠。

* `isFullScreen()` 获取页面是否全屏状态。

* `isScreenLock()` 获取旋转是否锁定。

* `isLandscape()` 获取页面是否是横屏。

* `isPortrait()` 判断是否竖屏。

* `getScreenRotation()` 获取屏幕旋转角度。返回 number

* `setLandscape()` 设置屏幕为横屏。

* `setPortrait()` 设置屏幕为竖屏。

* `getScreenSize()` 获取屏幕大小（宽高为竖屏时的大小）。

  * **返回**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | width | number  | 宽 |
  | height | number  | 高 |

### 分享扩展模块 ShareModule

* `shareFiles(options)`

  调用系统分享多个本地文件 。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | files | string[]  | 文件路径数组，必须是本地绝对路径 |
  | title | string  | 分享标题，默认是 “分享 %d 个文件”， %d 是文件个数。 |

* `shareFile(options)`

  调用系统分享本地文件 。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | file | string  | 文件路径，必须是本地绝对路径 |
  | title | string  | 分享标题，默认是 “分享文件” |

### 状态栏模块 StatusBarModule

* `setLightMode()` 设置状态栏为亮色模式
* `setDarkMode()` 设置状态栏为暗色模式
* `setStatusBarColor(color)` 设置状态栏颜色，颜色 #fff
* `getStatusBarBarHeight()` 获取状态栏的高度（像素）
* `getStatusBarIsLightMode()` 获取状态栏是否是亮色模式
* `getStatusBarVisible()` 获取状态栏是否显示

### 系统设置跳转工具模块 SystemSettingsModule

* `goSystemStartSettings()` 跳转到系统的当前App“自启动设置”页面，通常可以用于引导用户打开本APP的自启动
* `goSystemNotificationSetting()` 跳转到系统的当前App“通知设置”页面，通常可以用于引导用户打开本APP的通知权限
* `goSystemPermissionSetting()` 跳转到系统的当前App的权限页面，通常可以用于引导用户打开本APP的权限
* `goSystemAccessibilitySetting()` 跳转到系统的“辅助功能设置”页面，通常可以用于引导用户打开本APP的辅助功能权限
* `goSystemAirplaneModeSetting()` 跳转到系统的“飞行模式设置”页面.
* `goSystemWifiSetting()` 跳转到系统的“Wifi设置”页面.
* `goSystemApnSetting()` 跳转到系统的“APN设置”页面.
* `goSystemAppDetailsSetting(packageName)` 转到指定App的系统设置页面，packageName为包名
* `goSystemMyAppDetailsSetting()` 转到本App的系统设置页面
* `goSystemApplicationDevelopmentSetting()` 跳转到系统的“开发人员选项界面”页面.
* `goSystemApplicationSetting()` 跳转到系统的“应用程序列表界面”页面.
* `goSystemBluetoothSetting()` 跳转到系统的“蓝牙设置”页面.
* `goSystemDateSetting()` 跳转到系统的“日期时间设置”页面.
* `goSystemDataDoamingSetting()` 跳转到系统的“移动网络设置”页面.
* `goSystemDisplaySetting()` 跳转到系统的“手机显示”页面.
* `goSystemInputMethodsSetting()` 跳转到系统的“语言和输入设备设置”页面.
* `goSystemNetworkOperatorSetting()` 跳转到系统的“显示设置选择网络运营商设置”页面.
* `goSystemSetting()` 跳转到系统设置页面.

### 震动模块 VibrateModule

* `cancel()` 停止震动.
* `vibrate(ms)` 开始震动. ms 为震动时长，单位是毫秒

### 音量模块 VolumeModule

* streamType 对应android的AudioManager.STREAM_*，可选：
  * voiceCall
  * system
  * ring
  * music
  * alarm
  * notification
  * dtmf

* `getVolume(streamType)` 获取某个类型的音量

* `setVolume(options)`

  设置某个类型的音量。

  * **Options 参数**

  |  属性| 类型 |说明  |
  |  ----  | ----  | ----  |
  | streamType | string  | 见上方 |
  | showUI | boolean  | 是否显示UI，默认true |
  | vibrate | boolean  | 是否添加AudioManager.FLAG_VIBRATE，默认false |
  | playSound | boolean  | 是否添加AudioManager.FLAG_PLAY_SOUND，默认false |
