<template>
	<view class="content" ref="content">
		<view>
			<!-- 功能菜单 -->
			<view class="top-view">
				<view class="top-text">快捷操作</view>
				<view class="view-button">
					<view class="view-item" @click="clickModule('/pages/work/bpm/task/todo')">
						<uni-icons class="icon" type="calendar" size="60"></uni-icons>
						<text class="text">待办任务</text>
					</view>
					<view class="view-item" @click="clickModule('/pages/work/bpm/processInstance/processInstance')">
						<uni-icons type="compose" size="60"></uni-icons>
						<text class="text">已交任务</text>
					</view>
					<view class="view-item" @click="clickModule('/pages/work/contract/index')">
						<uni-icons type="wallet" size="60"></uni-icons>
						<text class="text">合同信息</text>
					</view>
					<view class="view-item" @click="toWeb()">
						<uni-icons type="link" size="60"></uni-icons>
						<text class="text">登录网页</text>
					</view>
				</view>
			</view>
		</view>
		<!-- 数据报表 -->
		<view class="info-view">
			<uni-section title="统计数据" type="square" class="section">
			</uni-section>
			<uni-grid :column="2" :showBorder="false" :square="false">
				<uni-grid-item>
					<view class="grid-item-box">
						<view class="text">待完成任务</view>
						<view class="value">{{info.todo}}</view>
					</view>
				</uni-grid-item>
				<uni-grid-item>
					<view class="grid-item-box">
						<view class="text">已签订合同</view>
						<view class="value">{{info.signed}}</view>
					</view>
				</uni-grid-item>
				<uni-grid-item>
					<view class="grid-item-box">
						<view class="text">已提交报告</view>
						<view class="value">{{info.commited}}</view>
					</view>
				</uni-grid-item>
				<uni-grid-item>
					<view class="grid-item-box">
						<view class="text">登录IP</view>
						<view class="value">{{info.ip}}</view>
					</view>
				</uni-grid-item>
			</uni-grid>
		</view>
		<!-- 底部填充 -->
		<view class="bottom-view">
			<!-- <uni-section title="" class="section"></uni-section> -->
			<canvas class="canvas" :disable-scroll="false" ref="canvas" canvas-id="canvas" id="canvas"
				@touchstart="start" @touchmove="move" @touchend="end" @touchcancel="end"></canvas>
			<!-- 鸡汤文字 -->
			<uni-card :is-shadow="false" ref="soup">
				<text class="soup">{{chickenSoup}}</text>
			</uni-card>
			<image class="hidden" ref="image" src= "/static/images/ball.png"></image>
		</view>
	</view>
</template>

<script>
	import {
		getChickenSoupApi,
		getIndexInfoApi
	} from '@/api/index.js'
	export default {
		onLoad() {
			// 获取系统信息
			const systemInfo = uni.getSystemInfoSync()
			this.screenHeight = systemInfo.screenHeight
		},
		data() {
			return {
				balls: [],
				number: 20,
				dx: 1,
				dy: 1,
				ballRadius: 10,
				screenHeight: '',
				bottom: '',
				cell: {
					status: false,
					x: 100,
					y: 100,
					radius: 50,
					color: 1
				},
				chickenSoup: "",
				info: {
					ip: "127.0.0.1",
					commited: 0,
					signed: 0,
					todo: 0
				}
			}
		},
		methods: {
			clickModule(path) {
				this.$tab.navigateTo(path)
			},
			toWeb() {
				this.$tab.view(this.$static.webUrl)
			},
			end(e) {
				this.cell.status = false
			},
			move(e) {
				this.cell.x = e.touches[0].x - this.cell.radius / 2
				this.cell.y = e.touches[0].y - this.cell.radius / 2
			},
			start(e) {
				this.cell.x = e.touches[0].x - this.cell.radius / 2
				this.cell.y = e.touches[0].y - this.cell.radius / 2
				this.cell.status = true
				this.cell.radius = 50
				this.cell.color = 1
			}
		},
		mounted() {
			getChickenSoupApi().then(res => {
				this.chickenSoup = res.data
			})
			getIndexInfoApi().then(res => {
				if (res && res.status != 404) {
					this.info = res.data
				}
			}).catch(res => {

			})
			
		},
		onShow() {
			
		},
		onReady() {
			var context = uni.createCanvasContext('canvas')
			var canvas = this.$refs.canvas.$el
			// 设置canvas高度达到最大高度
			// 获取元素的引用
			const that = this

			// var image = new Image()
			var src = []
			var status = 0

			function drawBall(x, y, radius, color) {
				context.beginPath()
				context.arc(x, y, radius, 0, Math.PI * 2);
				context.fillStyle = color;
				context.fill();
				context.closePath();
			}
			let deley = 0;
			// const canvas = this.$refs.canvas.$el
			const page = this.$refs.content.$el.parentElement.parentElement.parentElement
			// 判断是否有滑动条
			while (page.scrollHeight <= page.clientHeight) {
				canvas.style.height = canvas.clientHeight + 1 + 'px';
			}
			// 减小元素的高度，直到没有滑动条为止
			while (page.scrollHeight > page.clientHeight) {
				canvas.style.height = canvas.clientHeight - 1 + 'px';
				if (canvas.clientHeight == 0) {
					break;
				}
			}
			setInterval(()=>{
				if(page.scrollHeight > page.clientHeight){
					while (page.scrollHeight > page.clientHeight) {
						canvas.style.height = canvas.clientHeight - 1 + 'px';
						if (canvas.clientHeight == 0) {
							break;
						}
					}
				}
			})
			function drawCell() {
				deley++;
				context.drawImage(src[0], status % 4 * 128, parseInt(status / 4) * 128, 128, 128, that.cell.x, that
					.cell.y, that.cell.radius, that.cell.radius)
				if (deley > 4) {
					deley = 0
				} else {
					return
				}
				status = status + 1
				if (status == 16) {
					status = 0
				}
			}

			function clearCanvas() {
				context.clearRect(0, 0, canvas.clientWidth, canvas.clientHeight);
			}

			function randomizeDirection(dx, dy) {
				dx = Math.random() < 0.5 ? -dx : dx;
				dy = Math.random() < 0.5 ? -dy : dy;
				return {
					dx: dx,
					dy: dy
				}
			}

			function getRandomColor() {
				const letters = '0123456789ABCDEF';
				let color = '#';
				for (let i = 0; i < 6; i++) {
					color += letters[Math.floor(Math.random() * 16)];
				}
				return color;
			}

			function randomPoint() {
				const radius = Math.round((Math.random() * 10) + 10)
				const x = (Math.random() * (canvas.clientWidth - radius * 2)) + radius
				const y = (Math.random() * (canvas.clientHeight - radius * 2)) + radius
				const color = getRandomColor()
				const speedX = Math.round((Math.random() * 2) + 1)
				const speedY = Math.round((Math.random() * 2) + 1)
				return {
					x: x,
					y: y,
					radius: radius,
					color: color,
					speedX: speedX,
					speedY: speedY
				}
			}
			function draw() {
				if (that.balls.length < that.number) {
					const point = randomPoint()
					const x = point.x
					const y = point.y
					const radius = point.radius
					const color = point.color
					const speedX = point.speedX
					const speedY = point.speedY
					that.balls.push({
						x: x,
						y: y,
						dx: speedX,
						dy: speedY,
						radius: radius,
						color: color
					})
				}
				const newBalls = []
				for (const ball of that.balls) {
					// 碰撞墙壁
					if (ball.x + ball.dx > canvas.clientWidth - ball.radius || ball.x + ball.dx < that
						.ballRadius) {
						ball.dx = -ball.dx;
					}
					if (ball.y + ball.dy > canvas.clientHeight - ball.radius || ball.y + ball.dy < that
						.ballRadius) {
						ball.dy = -ball.dy;
					}
					var flag = true
					// 碰到球
					if (that.cell.status) {
						const distance = Math.pow((ball.x - that.cell.radius / 2 - that.cell.x), 2) + Math.pow((ball
							.y - that.cell.radius / 2 - that.cell.y), 2);
						if (Math.pow(ball.radius + that.cell.radius, 2) / 2 > distance) {
							flag = false
							if (that.cell.radius < 128) {
								that.cell.radius += 1
							}
						}
					}
					ball.x += ball.dx;
					ball.y += ball.dy;
					drawBall(ball.x, ball.y, ball.radius, ball.color);

					if (flag) {
						newBalls.push(ball)
					}
				}
				that.balls = newBalls;
				if (that.cell.status) {
					drawCell()
				}
				context.draw(false)
				requestAnimationFrame(draw)

			}
			function getUrl(relativeUrl) {
				const array = that.$refs.image.$el.baseURI.match(/(.*\/\/)(.+?)\/.*/);
				return array[1]+array[2]+relativeUrl
			}
			var tempImg = getUrl("/static/images/ball.png")
			src.push(tempImg)
			draw()
			// if(image){
			// 	image.onload = () => {
			// 		src.push(image.src)
			// 		draw()
			// 	}
			// }
			// image.src = "/static/images/ball.png"
		}
	}
</script>

<style lang='scss'>
	page {
		background-color: $uni-bg-color;
	}
	.hidden{
	width: 0;
	height: 0;
	opacity: 0;
	}
	.content {
		padding: 20rpx;

		.top-view {
			.top-text {
				position: absolute;
				top: 20rpx;
				line-height: 40rpx;
				font-size: $uni-font-size-lg;
				font-weight: 700;
				text-align: center;
				width: 100%;
				border-bottom: 10rpx #6fe16f dotted;
			}

			.view-button {
				display: flex;
				align-items: center;
				justify-content: space-evenly;

				.view-item {
					color: #649864;
				}

				.view-item:hover {
					color: #666;
				}

				.view-item:active {
					transform: scale(1.2);
				}
			}

			position: relative;
			background-color: #77e177;
			color: $uni-color-title;
			font-weight: 600;

			padding-top:70rpx;
			padding-bottom: 40rpx;

			.view-item {
				display: flex;
				flex-direction: column;
			}
		}

		.info-view {
			.section {
				/* font-size: $uni-font-size-lg; */

			}

			background-color: $uni-bg-color-grey;

			.grid-item-box {
				flex: 1;
				/* #ifndef APP-NVUE */
				display: flex;
				/* #endif */
				flex-direction: column;
				align-items: center;
				justify-content: space-between;
				background-color: $uni-bg-color;
				margin: 2px;
				padding: 15px 0;

				.text {
					line-height: 2em;
				}

				.value {
					color: #ee5021;
					line-height: 2em;
				}
			}
		}

		.bottom-view {
			.canvas {
				width: 100%;
				height: 400rpx;
				opacity: 0.7;
				z-index: 1;
			}
		}

		.soup {
			text-align: center;

			display: block;
		}
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>