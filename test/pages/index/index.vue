<template>
	<view class="content">
		<button @click="handleChooseImage">选择图片</button>
		<button @click="handleChooseImage2">选择图片2</button>
		<image :src="image" mode="widthFix" style="width:750rpx;"></image>
		<button @click="handleShowAlert">显示提示框</button>
		<button @click="handleShowVideoPicker">选择视频</button>
		<button @click="handleChooseImage">选择图片</button>
		<button @click="handleChooseFile">选择文件</button>
		<button @click="handleChooseFile2">选择文件2</button>
		<button @click="handleNotify">发送通知</button>
		<button @click="handleCancelNotify">取消通知</button>
	</view>
</template>

<script>
	const DialogModule = uni.requireNativePlugin('imengyu-AndroidToolbox-DialogModule');
	const GalleryModule = uni.requireNativePlugin('imengyu-AndroidToolbox-GalleryModule');
	const FilePickerModule = uni.requireNativePlugin('imengyu-AndroidToolbox-FilePickerModule');
	const NotificationModule = uni.requireNativePlugin('imengyu-AndroidToolbox-NotificationModule');
	export default {
		data() {
			return {
				image: '/static/logo.png'
			}
		},
		onLoad() {
		},
		methods: {
			handleChooseImage() {
				GalleryModule.chooseImage({
					compress: {
						width: 300,
						height: 300
					}
				}, (res) => {
					console.log(res);
					if(res.success) {
						this.image = 'file://' + res.tempFilePaths[0]
						console.log(this.image);
					}
				})
			},
			handleChooseImage2() {
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
