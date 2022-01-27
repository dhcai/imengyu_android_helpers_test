<template>
	<view class="content">
		<button @click="handleChooseImage">选择图片</button>
		<image :src="image" mode="widthFix" style="width:750rpx;"></image>
		<button @click="handleShowAlert">显示提示框</button>
		<button @click="handleShowVideoPicker">选择视频</button>
		<button @click="handleChooseImage">选择图片</button>
		<button @click="handleChooseImage">选择图片</button>
		<button @click="handleChooseImage">选择图片</button>
		<button @click="handleChooseImage">选择图片</button>
	</view>
</template>

<script>
	const DialogModule = uni.requireNativePlugin('imengyu-AndroidToolbox-DialogModule');
	const GalleryModule = uni.requireNativePlugin('imengyu-AndroidToolbox-GalleryModule');
	export default {
		data() {
			return {
				image: '/static/logo.png'
			}
		},
		onLoad() {
			console.log('DialogModule', DialogModule);
			console.log('GalleryModule', GalleryModule);
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
		}
	}
</script>

<style>

</style>
