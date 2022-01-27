/**
 * 对话框mixin。
 * 高性能对话框组件，组件全部由原生实现。
 * 同时，如果原生不支持，则使用uni自带相关组件显示。
 * 
 * 模块名称: DialogModule
 */

export default {
	data() {
		return {
			dialogModule: null,
		}
	},
	mounted() {
		this.dialogModule = uni.requireNativePlugin('imengyu-AndroidToolbox-DialogModule')
	},
  methods: {
		/**
		 * 设置对话框组件主题
		 * @param {Object} option
     * {
     *     dialogStyle: 'Material'|'Kongzue'|'IOS'|'MIUI', //对话框样式
     *     dialogTheme: 'LIGHT'|'DARK', //对话框主题
     * }
		 */
		setTheme(option) {
			if(this.dialogModule)
				this.dialogModule.setTheme(option);
		},
		/**
		 * 显示土司
		 * @param {String} string 土司文字
		 */
		showToast(string) {
			if(this.dialogModule)
				this.dialogModule.showToast({
					title: string,
				});
			else
				uni.showToast({
					icon: 'none',
					title: string
				})
		},
		/**
		 * 显示消息提示框
		 * @param {Object} options 
		 * {
		 *     title?: string, //对话框标题
		 *     message?: string, //对话框文字
		 *     okText?: string, //确定按钮文字，默认“确定”
		 *     cancelText?: string, //取消按钮文字，默认“取消”，为空则不显示取消按钮
		 *     otherText?: string, //第三按钮文字，默认“”，为空则不显示第三按钮
     *     dialogStyle?: 'Material'|'Kongzue'|'IOS'|'MIUI', //对话框样式
     *     dialogTheme?: 'LIGHT'|'DARK', //对话框主题
		 *     onOk?: () => void,
		 *     onCancel?: () => void,
		 *     onOther?: () => void,
		 * }
		 */
		showMessageDialog(options) {
			if(this.dialogModule)
				this.dialogModule.showMessageDialog(options, (res) => {
					if(res.confirm == 'ok') {
						if(typeof options.onOk == 'function') options.onOk();
					} else if(res.confirm == 'cancel') {
						if(typeof options.onCancel == 'function') options.onCancel();
					} else if(res.confirm == 'other') {
						if(typeof options.onOther == 'function') options.onOther();
					}
				});
			else
				uni.showModal({
					title: options.title,
					content: options.message,
					confirmText: options.okText || '确定',
					cancelText: options.cancelText || '取消',
					showCancel: typeof options.cancelText === 'string' && options.cancelText != '',
					success(res) {
						if(res.confirm) {
							if(typeof options.onConfirm == 'function') options.onConfirm();
						} else {
							if(typeof options.onCancel == 'function') options.onCancel();
						}
					}
				})
		},
		/**
		 * 显示原生输入对话框
		 * @param {Object} options 
		 * {
		 *     title?: string, //对话框标题
		 *     message?: string, //对话框文字
		 *     okText?: string, //确定按钮文字，默认“确定”
		 *     cancelText?: string, //取消按钮文字，默认“取消”
		 *     hintText?: string, //输入框提示文字
		 *     initialText?: string, //输入框开始时的文字
     *     dialogStyle?: 'Material'|'Kongzue'|'IOS'|'MIUI', //对话框样式
     *     dialogTheme?: 'LIGHT'|'DARK', //对话框主题
		 *     onConfirm?: (inputStr) => void,
		 *     onCancel?: (inputStr) => void,
		 * }
		 */
		showInputDialog(options) {
			if(this.dialogModule)
				this.dialogModule.showMessageDialog(options, (res) => {
					if(res.confirm) {
						if(typeof options.onConfirm == 'function') options.onConfirm(res.inputStr);
					} else {
						if(typeof options.onCancel == 'function') options.onCancel(res.inputStr);
					}
				});
			else
				uni.showModal({
					title: options.title,
					content: options.message,
					confirmText: options.okText || '确定',
					cancelText: options.cancelText || '取消',
					showCancel: typeof options.cancelText === 'string' && options.cancelText != '',
					placeholderText: options.hintText,
					editable: true,
					success(res) {
						if(res.confirm) {
							if(typeof options.onConfirm == 'function') options.onConfirm(res.input);
						} else {
							if(typeof options.onCancel == 'function') options.onCancel(res.input);
						}
					}
				})
		},
		/**
		 * 显示原生底部菜单
		 * @param {object} options
		 * {
		 *     title: string, //对话框标题
		 *     choices: string[], //条目文字
     *     dialogStyle?: 'Material'|'Kongzue'|'IOS'|'MIUI', //对话框样式
     *     dialogTheme?: 'LIGHT'|'DARK', //对话框主题
		 * 		 onSelect: (res) => void //选择回调
		 * }
		 * 回调res参数：
		 * {
		 *     chooseIndex: number, //选中的条目索引
		 *     chooseText: string, //选中的条目文字
		 * }
		 */
		showBottomMenu(options) {
			if(this.dialogModule)
				this.dialogModule.showBottomMenu(options, (res) => {
					if(typeof options.onSelect == 'function') options.onSelect(res);
				});
			else
				uni.showActionSheet({
					title: options.title,
					itemList: options.choices,
					success(res) {
						if(typeof options.onSelect == 'function') options.onSelect({
							chooseIndex: res.tapIndex,
							chooseText: options.choices[res.tapIndex]
						});
					}
				})
		},
		/**
		 * 显示原生加载中对话框
		 * @param {object} options
		 * {
		 *     title: string, //标题
		 *     onShow: (res) => void //显示回调
		 * }
		 * 回调返回res 数据:
		 * {
		 *     dialogId: number, //当前对话框ID，可以使用此ID进行后续操作
		 *     success: boolean,
		 *     errMsg: string
		 * }
		 */
		showLoading(options) {
			if(this.dialogModule)
				this.dialogModule.showLoading(options, (res) => {
					if(typeof options.onShow == 'function') options.onShow(res);
				});
			else 
				uni.showLoading({
					title: options,
					mask: true,
					success() {
						if(typeof options.onShow == 'function') options.onShow({
							dialogId: 0,
							success: true,
						});
					},
					fail(res) {
						if(typeof options.onShow == 'function') options.onShow({
							dialogId: 0,
							success: false,
							errMsg: res.errMsg
						});
					}
				})
		},
		/**
		 * 隐藏原生加载中对话框
		 * @param {number} id showLoading 返回的ID, 如果不提供，则关闭所有
		 */
		hideLoading(id) {
			if(this.dialogModule)
				this.dialogModule.hideLoading({ id });
			else
				uni.hideLoading();
		},
		/**
		 * 更新原生加载中对话框的百分比
		 * @param {object} options
		 * {
		 *     id: number, //showLoading 返回的ID
		 *     progress: number, //百分比，0-1
		 * }
		 */
		setLoadingProgress(options) {
			if(this.dialogModule)
				this.dialogModule.setLoadingProgress(options);
		},
		/**
		 * 显示原生提示对话框
		 *  @param {object} options
		 * {
		 *     title: string, //标题
		 *     type: 'success'|'warn'|'error'|'none', 提示类型
		 *     duration: number, //显示时长，毫秒
     *     dialogStyle?: 'Material'|'Kongzue'|'IOS'|'MIUI', //对话框样式
     *     dialogTheme?: 'LIGHT'|'DARK', //对话框主题
		 *     onShow: (res) => void //显示回调
		 * }
		 *  回调返回res数据
		 * {
		 *     dialogId: number, //当前对话框ID，可以使用此ID进行后续操作
		 *     success: boolean,
		 *     errMsg: string
		 * }
		 */
		showTipDialog(options) {
			if(this.dialogModule)
				this.dialogModule.showTipDialog(options, (res) => {
					if(typeof options.onShow == 'function') options.onShow(res);
				});
			else
				uni.showToast({
					icon: options.type,
					title: options.title,
					duration: options.duration,
					success() {
						if(typeof options.onShow == 'function') options.onShow({
							dialogId: 0,
							success: true,
						});
					}
				})
		},
		/**
		 * 隐藏原生提示对话框
		 * @param {number} id showLoading 返回的ID
		 */
		hideTipDialog(id) {
			if(this.dialogModule)
				this.dialogModule.hideTipDialog({ id });
			else
				uni.hideToast();
		},
		/**
     * 显示原生分享对话框
     * @param options
     * {
     *     choices: string[], //可选分享类型 wechat-moments，qq，wechart，sina，link，poster，weapp-qrcode，qrcode
		 *     onSelect: (res) => void //选择回调。取消不会返回回调
     * }
     * 回调 res :
     * {
     *     index: number, //选中条目索引
     *     type: string, //选中的条目名称
     * }
     */
    showShareDialog(options) {
			if(this.dialogModule)
				this.dialogModule.showShareDialog(options, (res) => {
					if(typeof options.onSelect == 'function') options.onSelect(res);
				});
			else {
				const choices = options.choices;
				const choicesNames = new Map();
				choicesNames.set('wechat', '微信');
				choicesNames.set('wechat-moments', '微信朋友圈');
				choicesNames.set('qq', 'QQ');
				choicesNames.set('sina', '新浪微博');
				choicesNames.set('link', '复制分享链接');
				choicesNames.set('poster', '生成海报分享');
				choicesNames.set('weapp-qrcode', '小程序码');
				choicesNames.set('qrcode', '二维码');
				uni.showActionSheet({
					itemList: choices.map(n => choicesNames.get(n)),
					success(res) {
						if(typeof options.onSelect == 'function') options.onSelect({
							index: res.tapIndex,
							type: choices[res.tapIndex],
						});
					}
				})
			}
		},
		/**
		 * 显示原生确认对话框
		 * @param {Object} options
		 * {
		 *     title: string, //对话框标题
		 *     message: string, //对话框文字
		 *     okText: string, //确定按钮文字，默认“确定”
		 *     cancelText: string, //取消按钮文字，默认“取消”
		 *     cancelable: boolean, //是否可以点击外围区域关闭对话框
		 *     onConfirm: () => void,
		 *     onCancel: () => void,
		 * }
		 */
		showConfirmDialog(options) {
			if(this.dialogModule)
				this.dialogModule.showConfirmDialog(options, (res) => {
						if(res.confirm) {
							if(typeof options.onConfirm == 'function') options.onConfirm();
						} else {
							if(typeof options.onCancel == 'function') options.onCancel();
						}
				});
			else {
				uni.showModal({
					title: options.title || '确认提示',
					content: options.message,
					cancelText: options.cancelText || '取消',
					showCancel: options.cancelText != '',
					confirmText: options.okText || '确定', 
					success(res) {
						if(res.confirm) {
							if(typeof options.onConfirm == 'function') options.onConfirm();
						} else {
							if(typeof options.onCancel == 'function') options.onCancel();
						}
					},
				})
			}
		},
		/**
		 * 显示 PopTip
		 * @param {Object} options
		 * {
     *     title: string, //文字
     *     icon: ''|'success'|'error'|'warning'|'build'|'check'|'bug_report', //图标
     *     buttonText: string, //按钮文字
     *     duration?: number, //隐藏时间，毫秒
     *     dialogStyle?: 'Material'|'Kongzue'|'IOS'|'MIUI', //对话框样式
     *     dialogTheme?: 'LIGHT'|'DARK', //对话框主题
		 *     onClickButton: () => void, //点击自定义按钮时回调
		 *     onClickTip: () => void, //点击 PopTip 本身时回调
		 * }
		 */
		showPopTip(options) {
			if(this.dialogModule)
				this.dialogModule.showPopTip(options, (res) => {
						if(res.click == 'tip') {
							if(typeof options.onClickTip == 'function') options.onClickTip(res.inputStr);
						} else if(res.click == 'button')  {
							if(typeof options.onClickButton == 'function') options.onClickButton(res.inputStr);
						}
				});
			else
				uni.showToast({
					icon: 'none',
					title: options.title
				})
		},
		
  }
}