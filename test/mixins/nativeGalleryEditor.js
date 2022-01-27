/**
 * Author：imengyu 2021-12-16
 * 
 * 原生图片剪裁器与视频剪裁器
 * 
 * 图片剪裁器基于 Ucrop
 * 视频剪裁器基于 FFmpeg
 * 
 * 模块名称: GalleryModule
 */

export default {
	data() {
		return {
			galleryModule: null,
		}
	},
	mounted() {
		this.galleryModule = uni.requireNativePlugin('imengyu-AndroidToolbox-GalleryModule')
	},
  methods: {
		/**
		 * 开启视频剪裁窗口
		 * @param options
		 *                  {
		 *                      path: string, //视频的路径
		 *                      title: string, //剪裁窗口顶部显示的文字
		 *                      maxDuration: number, //最大可剪裁时长，单位秒，默认60秒
		 *                  }
		 * @param callback
		 *                  {
		 *                      tempVideoPath: string, //剪裁完成后的视频的路径。使用完之后请使用deleteTempVideo删除
		 *                      success: boolean //获取是否成功
		 *                  }
		 */
		openVideoEditor(options, callback) {
			if(this.galleryModule)
				this.galleryModule.startVideoEditActivity(options, callback);
			else
				throw new Error("不支持的平台。请确认是否是 APP/IOS ，并且在打包了原生插件的自定义基座中运行。");
		},
		/**
		 * 打开裁剪图片窗口 (UCorp)
		 * @param options
		 * {
     *      sourceFilePath: string, //源文件路径
     *      useSourceImageAspectRatio?: boolean, //是否使用源图像比例剪裁
     *      aspectRatio?: number[], //是否使用指定比例剪裁，当useSourceImageAspectRatio为true时此值无效
     *      maxResultSize?: number[], //最大可以允许的图片的长宽
     *      activeControlsWidgetColor?: string, //控件激活状态颜色
     *      cropFrameColor?: string, //剪裁框颜色
     *      cropGridColor?: string, //剪裁框网格颜色
     *      dimmedLayerColor?: string, //裁剪边界周围暗显区域的所需颜色
     *      logoColor?: string, //徽标填充所需的解析颜色（默认为深灰色）
     *      rootViewBackgroundColor?: string, //根视图的背景色
     *      statusBarColor?: string, //状态栏颜色
     *      toolbarColor?: string, //工具栏颜色
     *      toolbarWidgetColor?: string, //工具栏文本和按钮所需的解析颜色（默认为深橙色）
     *      toolbarTitle?: string, //工具栏标题
     *      compressionQuality?: number, //[0 - 100] 剪裁图片压缩质量，png图片无此参数
     *      compressionFormat?: 'jpeg'|'png'|'webp', //设置压缩图片格式
     *      circleDimmedLayer?: boolean, //设置是否是圆形剪裁框
     *      showCropFrame?: boolean, //设置是否显示剪裁框
     *      showCropGrid?: boolean, //设置是否显示剪裁框网格
     *      freeStyleCropEnabled?: boolean, //设置是否可以自由调整剪裁框
     *      hideBottomControls?: boolean, //设置是否显隐藏底部控件
     *      maxScaleMultiplier?: integer, //此方法设置用于从最小图像比例计算最大图像比例的乘数。
     *      maxBitmapSize?: integer, //用于设置从输入Uri解码并在视图中使用的位图宽度和高度的最大大小。
     *      imageToCropBoundsAnimDuration?: integer, //此方法设置图像的动画持续时间，以包裹裁剪边界
     *      cropFrameStrokeWidth?: integer, //裁剪帧线的宽度（以像素为单位）
     *      cropGridRowCount?: integer, //裁剪网格的行数。
     *      cropGridColumnCount?: integer, //裁剪网格的列数。
		 * }
		 * @param callback
		 * {
		 *      success: boolean,
		 *      errMsg: string,
		 *      tempFilePath: string, //剪裁后的临时图片路径
		 * }
		 */
		openImageCropEditor(options, callback) {
			if(this.galleryModule)
				this.galleryModule.startCropImageActivity(options, callback);
			else
				throw new Error("不支持的平台。请确认是否是 APP/IOS ，并且在打包了原生插件的自定义基座中运行。");
		},
		/**
		 * 删除视频剪裁器剪裁生成的临时视频 【异步】
		 * @param options
		 *                  {
		 *                      path: string //视频的路径
		 *                  }
		 * @param callback  回调
		 *                  {
		 *                      success: boolean, //是否成功
		 *                      errMsg: string //如果失败，则返回异常信息
		 *                  }
		 */
		deleteVideoEditorTempVideo(options, callback) {
			if(this.galleryModule)
				this.galleryModule.deleteTempVideo(options, callback);
			else
				throw new Error("不支持的平台。请确认是否是 APP/IOS ，并且在打包了原生插件的自定义基座中运行。");
		},
		/**
		 * 获取本地视频宽高与大小数据 【异步】
		 * @param options
		 *                  {
		 *                      path: string //视频的路径
		 *                  }
		 * @param callback  回调
		 * 返回参数：
		 *                  {
		 *                      width: number,
		 *                      height: number,
		 *                      size: number,//大小，字节
		 *                      rotation: number,//视频的方向角度
		 *                  }
		 */
		getLocalVideoSize(options, callback) {
			if(this.galleryModule)
				this.galleryModule.getVideoSize(options, callback);
			else
				throw new Error("不支持的平台。请确认是否是 APP/IOS ，并且在打包了原生插件的自定义基座中运行。");
		},
  }
}