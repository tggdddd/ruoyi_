<template>
	<view class="normal-login-container">
		<view class="bg-bubbles">
		<view></view>
		<view></view>
		<view></view>
		<view></view>
		<view></view>
		<view></view>
		<view></view>
		<view></view>
		<view></view>
		<view></view>
		</view>
		<view class="logo-content align-center justify-center flex">
			<image class="moveImage1" style="width: 100rpx;height: 100rpx;" :src="globalConfig.appInfo.logo" mode="widthFix">
			</image>
			<text class="title">合同管理系统</text>
			<image class="moveImage2" style="width: 100rpx;height: 100rpx;" :src="globalConfig.appInfo.logo" mode="widthFix">
			</image>
		</view>
		<view class="login-form-content">
			<view class="input-item flex align-center">
				<view class="iconfont icon-user icon"></view>
				<input v-model="loginForm.username" class="input" type="text" placeholder="请输入账号" maxlength="30" />
			</view>
			<view class="input-item flex align-center">
				<view class="iconfont icon-password icon"></view>
				<input v-model="loginForm.password" type="password" class="input" placeholder="请输入密码" maxlength="20" />
			</view>
			<Verify @success="pwdLogin" :mode="'pop'"
			 :captchaType="'blockPuzzle'"
				:imgSize="{ width: '330px', height: '155px' }" ref="verify"></Verify>
			<view class="action-btn">
				<button @click="handleLogin" class="login-btn cu-btn block bg-blue lg round">登录</button>
			</view>
		</view>

		<view class="xieyi text-center">
			<text class="text-grey1">登录即代表同意</text>
			<text @click="handleUserAgrement" class="text-blue">《用户协议》</text>
			<text @click="handlePrivacy" class="text-blue">《隐私协议》</text>
		</view>
		</view>
	</view>
</template>

<script>
	import Verify from "@/components/verifition/Verify"

	export default {
		name: 'Login',
		components: {
			Verify
		},
		data() {
			return {
				captchaEnabled: false, // 验证码开关 TODO 芋艿：需要抽到配置里
				globalConfig: getApp().globalData.config,
				loginForm: {
					username: "admin",
					password: "123456",
					captchaVerification: ""
				}
			}
		},
		methods: {
			// 隐私协议
			handlePrivacy() {
				let site = this.globalConfig.appInfo.agreements[0]
				this.$tab.navigateTo(`/pages/common/webview/index?title=${site.title}&url=${site.url}`)
			},
			// 用户协议
			handleUserAgrement() {
				let site = this.globalConfig.appInfo.agreements[1]
				this.$tab.navigateTo(`/pages/common/webview/index?title=${site.title}&url=${site.url}`)
			},
			// 登录方法
			async handleLogin(params) {
				if (this.loginForm.username === "") {
					this.$modal.msgError("请输入您的账号")
				} else if (this.loginForm.password === "") {
					this.$modal.msgError("请输入您的密码")
				} else {
					// 显示验证码
					if (this.captchaEnabled) {
						this.$refs.verify.show()
					} else { // 直接登录
						await this.pwdLogin({})
					}
				}
			},
			// 密码登录
			async pwdLogin(captchaParams) {
				this.$modal.loading("登录中，请耐心等待...")
				// 执行登录
				this.loginForm.captchaVerification = captchaParams.captchaVerification
				this.$store.dispatch('Login', this.loginForm).then(() => {
					this.$modal.closeLoading()
					this.loginSuccess()
				})
			},
			// 登录成功后，处理函数
			loginSuccess(result) {
				// 设置用户信息
				this.$store.dispatch('GetInfo').then(res => {
					this.$tab.reLaunch('/pages/index')
				})
				// 获得字典数据
				this.$store.dispatch('Init')
			}
		}
	}
</script>

<style lang="scss">
	page {
		// background: linear-gradient(to right top, #50a1a2 0%, #b3f3a6 100%);
		background:  #a3d3b6 ;
	}
	
	.normal-login-container {
		width: 100%;
		.logo-content {
			width: 100%;
			font-size: 21px;
			text-align: center;
			padding-top: 15%;

			image {
				border-radius: 4px;
			}

			.title {
				margin-left: 10px;
			}
			.moveImage1{
				animation: downAndUp 2s infinite cubic-bezier(0.38, 0.4, 0.6, 0.67);
			}
			.moveImage2{
				animation: downAndUp 2s infinite cubic-bezier(0.38, 0.6, 0.4, 0.67);
				animation-delay: -1s;
			}
			@keyframes downAndUp {
				0%{
					top: 0rpx;
				}
				25%{
					top: 50rpx;
				}
				50%{
					top: 0rpx;
				}
				75%{
					top: -50rpx;
				}
				100%{
					top: 0rpx;
				}
			}
		}
		.z-top{
			z-index: 999;
		}
		.login-form-content {
			text-align: center;
			margin: 20px auto;
			margin-top: 15%;
			width: 80%;

			.input-item {
				margin: 20px auto;
				background-color: #f5f6f7;
				height: 45px;
				border-radius: 20px;

				.icon {
					font-size: 38rpx;
					margin-left: 10px;
					color: #999;
				}

				.input {
					width: 100%;
					font-size: 14px;
					line-height: 20px;
					text-align: left;
					padding-left: 15px;
				}

			}

			.login-btn {
				margin-top: 40px;
				height: 45px;
			}

			.xieyi {
				color: #333;
				margin-top: 20px;
			}
		}

		.easyinput {
			width: 100%;
		}
	}

	.login-code-img {
		height: 45px;
	}
	.bg-bubbles {
	  position: absolute;
	  top: 0;
	  left: 0;
	  width: 100%;
	  height: 100%;
	}
	.bg-bubbles view {
	  position: absolute;
	  list-style: none;
	  display: block;
	  width: 40px;
	  height: 40px;
	  background-color: rgba(255, 255, 255, 0.15);
	  bottom: -160px;
	  -webkit-animation: square 25s infinite;
	  animation: square 25s infinite;
	  transition-timing-function: linear;
	}
	.bg-bubbles view:nth-child(1) {
	  left: 10%;
	}
	.bg-bubbles view:nth-child(2) {
	  left: 20%;
	  width: 80px;
	  height: 80px;
	  -webkit-animation-delay: 2s;
	          animation-delay: 2s;
	  -webkit-animation-duration: 17s;
	          animation-duration: 17s;
	}
	.bg-bubbles view:nth-child(3) {
	  left: 25%;
	  -webkit-animation-delay: 4s;
	          animation-delay: 4s;
	}
	.bg-bubbles view:nth-child(4) {
	  left: 40%;
	  width: 60px;
	  height: 60px;
	  -webkit-animation-duration: 22s;
	          animation-duration: 22s;
	  background-color: rgba(255, 255, 255, 0.25);
	}
	.bg-bubbles view:nth-child(5) {
	  left: 70%;
	}
	.bg-bubbles view:nth-child(6) {
	  left: 80%;
	  width: 120px;
	  height: 120px;
	  -webkit-animation-delay: 3s;
	          animation-delay: 3s;
	  background-color: rgba(255, 255, 255, 0.2);
	}
	.bg-bubbles view:nth-child(7) {
	  left: 32%;
	  width: 160px;
	  height: 160px;
	  -webkit-animation-delay: 7s;
	          animation-delay: 7s;
	}
	.bg-bubbles view:nth-child(8) {
	  left: 55%;
	  width: 20px;
	  height: 20px;
	  -webkit-animation-delay: 15s;
	          animation-delay: 15s;
	  -webkit-animation-duration: 40s;
	          animation-duration: 40s;
	}
	.bg-bubbles view:nth-child(9) {
	  left: 25%;
	  width: 10px;
	  height: 10px;
	  -webkit-animation-delay: 2s;
	          animation-delay: 2s;
	  -webkit-animation-duration: 40s;
	          animation-duration: 40s;
	  background-color: rgba(255, 255, 255, 0.3);
	}
	.bg-bubbles view:nth-child(10) {
	  left: 90%;
	  width: 160px;
	  height: 160px;
	  -webkit-animation-delay: 11s;
	          animation-delay: 11s;
	}
	@-webkit-keyframes square {
	  0% {
	    transform: translateY(0);
	  }
	  100% {
	    transform: translateY(-700px) rotate(600deg);
	  }
	}
	@keyframes square {
	  0% {
	    transform: translateY(0);
	  }
	  100% {
	    transform: translateY(-700px) rotate(600deg);
	  }
	}
</style>