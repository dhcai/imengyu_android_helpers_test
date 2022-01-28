<template>
	<view class="content">
		<button @click="handleChooseImage">选择图片</button>
		<image :src="image" mode="widthFix" style="width:750rpx;"></image>
		<button @click="handleShowAlert">显示提示框</button>
		<button @click="handleCutImage">图片剪裁</button>
		<button @click="handleCutVideo">视频剪裁</button>
		<button @click="handleShowVideoPicker">选择视频</button>
		<button @click="handleShareDalog">shareDalog</button>
		<button @click="handleChooseFile">选择文件</button>
		<button @click="handleChooseFile2">选择文件2</button>
		<button @click="handleNotify">发送通知</button>
		<button @click="handleCancelNotify">取消通知</button>
		<GSYVideoPlayer
			style="width:750rpx;height:500rpx;"
			:src="video"
		 ></GSYVideoPlayer>
	</view>
</template>

<script>
	const DialogModule = uni.requireNativePlugin('imengyu-AndroidToolbox-DialogModule');
	const GalleryModule = uni.requireNativePlugin('imengyu-AndroidToolbox-GalleryModule');
	const FilePickerModule = uni.requireNativePlugin('imengyu-AndroidToolbox-FilePickerModule');
	const NotificationModule = uni.requireNativePlugin('imengyu-AndroidToolbox-NotificationModule');
	import nativeGalleryEditor from '@/mixins/nativeGalleryEditor.js'
	export default {
		data() {
			return {
				image: '/static/logo.png',
				video: 'https://imengyu.top/assets/images/test/test.mp4',
			}
		},
		onLoad() {
		},
		mixins: [ nativeGalleryEditor, ],
		methods: {
			handleCutImage() {
				GalleryModule.chooseImage({
				}, (res) => {
					if(res.success) {
						const path = plus.io.convertLocalFileSystemURL(res.tempFilePaths[0]);
						this.openImageCropEditor({
							sourceFilePath: path,
							aspectRatio: [ 1, 1 ],
						}, (res) => {
								console.log(res);
							if(res.success) {
								this.image = 'file://' + res.tempFilePath;
							}
						});
					}
				})
			},
			handleCutVideo() {
				GalleryModule.chooseVideo({
					 showCameraButton: false,
				}, (res) => {
					console.log(res);
					if(res.success) {
						this.openVideoEditor({
							path: res.chooseVideoPath,
							title: '测试剪裁',
						}, (res) => {
							console.log(res);
							if(res.success) {
								this.video = res.tempFilePath
							}
						});
					}
				})
			},
			handleChooseImage() {
				GalleryModule.chooseImage({
					compress: {
						width: 400,
					}
				}, (res) => {
					console.log(res);
					if(res.success) {
						this.image = 'file://' + res.tempFilePaths[0]
						console.log(this.image);
					}
				})
			},
			handleShowAlert() {
				DialogModule.showMessageDialog({
					title: '提示', 
					message: 'showMessageDialog 这个对话框不错哦',
				}, (res) => {
					console.log(res);
				})
			},
			handleShowVideoPicker() {
				GalleryModule.chooseVideo({
				}, (res) => {
					console.log(res);
				})
			},
			handleShareDalog() {
				DialogModule.showShareDialog({
					choices: [ 'wechat-moments','qq','wechat','sina','link','poster','weapp-qrcode','qrcode'],
				}, (res) => {
					console.log(res);
				});
			},
			handleChooseFile() {
				FilePickerModule.pickFiles({
					type: '*/*',
					maxCount: 1,
				}, (res) => {
					console.log(res);
				})
			},
			handleChooseFile2() {
				FilePickerModule.pickFiles({
					type: 'image/*',
					maxCount: 2,
				}, (res) => {
					console.log(res);
				})
			},
			handleNotify() {
				NotificationModule.notify({
					id: 1,
					title: '测试通知',
					text: '测试通知内容内容内容内容内容',
				});
			},
			handleCancelNotify() {
				NotificationModule.cancelAll();
			},
		}
	}
</script>

<style>

</style>
