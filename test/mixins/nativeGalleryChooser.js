/**
 * 原生相册选择mixin
 * 
 * Uniapp的选择器老是有显示很多黑色的图片，没人解决这个问题。
 * 请用这个原生组件。
 * 提供了高性能选择器，提供了访问本地相册，视频的能力。适配（Android 11）
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
		 * 获取系统相册的所有相册以及图片数据【异步】
		 * @param options {}
		 * @param callback  回调
		 * 返回参数：
		 *                  {
		 *                      list: [
		 *                          //相册列表：
		 *                          {
		 *                              name: string,
		 *                              folderPath: string,
		 *                              coverImagePath: string,
		 *                              //相册图片列表：
		 *                              photos: [
		 *                                  name: string,//图片名称
		 *                                  path: string,//图片全路径
		 *                                  type: string,//图片类型
		 *                                  width: number,//图片宽度
		 *                                  height: number,//图片高度
		 *                                  orientation: number,//图片旋转角度
		 *                                  size: number,//图片文件大小，单位：Bytes
		 *                                  duration: number,//视频时长，单位：毫秒
		 *                                  time: number
		 *                              ]
		 *                          }
		 *                      ]
		 *                  }
		 */
		getSystemAlbums(options, callback) {
			if(this.galleryModule)
				this.galleryModule.getSystemAlbums(options, callback);
			else
				throw new Error("不支持的平台。请确认是否是 APP/IOS ，并且在打包了原生插件的自定义基座中运行。");
		},
		/**
		 * 获取系统相册所有视频数据 【异步】
		 * @param options {}
		 * @param callback  回调
		 * 返回参数：
		 * {
     *     videos: {
     *        duration: number,
     *        path: string,
     *        name: string,
     *        size: number,
     *     }[],
		 * }
		 */
		getSystemVideos(options, callback) {
			if(this.galleryModule)
				this.galleryModule.getSystemVideos(options, callback);
			else
				throw new Error("不支持的平台。请确认是否是 APP/IOS ，并且在打包了原生插件的自定义基座中运行。");
		},
		/**
		 * 选择本地图片
		 * @param options
		 * {
		 *     sourceType: [ 'album', 'camera' ], //同 uni.chooseImage sourceType
		 *     count: number, //可选最大数量
		 *     hasGif: boolean, //是否显示动图，默认true
		 *     hasVideo: boolean, //是否显示视频，默认false
		 *     needSize: boolean, //是否需要获取图像宽高，默认false
     *     compressQuality: number,	//压缩图片的质量，取值范围为1-100，数值越小，质量越低。默认值为80。
     *     compress: { //如果指定此参数，返回的图片均会进行强制压缩，并且调整到您设置的大小.
     *         //如果您至指定了长宽其中某个值，则会按比例为你缩小图片。
     *         //如果您至指定了长宽其两个值，则会按您设置的长宽直接拉伸图片。
     *         width: Number, //目标压缩宽度，单位为px，用于计算裁剪宽高比。
     *         height: Number, //目标压缩高度，单位为px，用于计算裁剪宽高比。
     *     },
     *     imageDirectionCorrection: number, //纠正图片的旋转方向，默认旋转方向是依照照片的拍摄方向，你可以通过设置这个值，让图片在默认旋转方向上再旋转多少度。
		 * }
		 * @param callback 回调，大致与 uni.chooseImage 相同
		 * {
		 *      success: boolean, 
		 *      errMsg: string,
		 *      isOriginal: boolean,  //是否选中了原图
		 *      tempFilePaths: string[], //选中图像信息路径数组
		 *      tempFiles: {
		 *                 name: string, //文件名
		 *                 width: number, //宽，必须指定needSize为true才有效
		 *                 height: number, //高，必须指定needSize为true才有效
		 *                 type: string, //图片类型
		 *                 duration: number, //视频的时长
		 *                 orientation: number, //图片的旋转方向
		 *                 time: number, //图片创建时间戳
		 *                 path: string //图片完整路径
		 *      }[], //选中图像信息
		 * }
		 */
		chooseImage(options, callback) {
			if(this.galleryModule)
				this.galleryModule.chooseImage(options, callback);
			else 
				uni.chooseImage({
					extension: options.hasGif ? undefined : [ '.png','.jpg','.jpeg','.bmp','.webp' ],
					sourceType: options.sourceType,
					count: options.count,
					success(res) {
						callback({
							success: true,
							errMsg: 'ok',
							isOriginal: false,
							tempFilePaths: res.tempFilePaths,
							tempFiles: res.tempFiles,
						});
					},
					fail(res) {
						callback({
							success: false,
							errMsg: res.errMsg
						});
					}
				})
		},
		/**
		 * 选择本地视频
		 * @param options
		 * {
		 *     title: string, //对话框标题
     *     showCameraButton: boolean, //是否显示相机按钮, 默认显示
		 * }
		 * @param callback 回调
		 * {
		 *     chooseVideo: {
		 *        duration: number, //时长ms
		 *        path: string,  //路径
		 *        name: string,  //名称
		 *        size: number,  //大小字节
		 *     },
		 *     chooseVideoPath: string,  //选中的视频路径，同 chooseVideo.path，如果选中的是相机按钮，则为 "camera"
		 *     success: boolean, //是否成功
		 *     errMsg: string
		 * }
		 */
		chooseVideo(options, callback) {
			if(this.galleryModule)
				this.galleryModule.chooseVideo(options, callback);
			else 
				uni.chooseVideo({
					success(res) {
						callback({
							success: false,
							errMsg: 'ok',
							chooseVideo: {
								duration: res.duration,
								path: res.tempFilePath,
								name: res.name,
								size: res.size,
							},
							chooseVideoPath: res.tempFilePath,
						});
					},
					fail(res) {
						callback({
							success: false,
							errMsg: res.errMsg
						});
					}
				})
		},
  }
}