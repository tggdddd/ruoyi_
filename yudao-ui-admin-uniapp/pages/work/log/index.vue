<template>
	<view>
		<!-- 搜索栏 -->
		<query-component :queryParams="queryParams" @update:queryParams="queryParams = $event" :columns="columns" @confirm="confirm" />
		<scroll-view class="scroll" scroll-y @scrolltolower="loadMore()">

			<!-- 表格显示内容 -->
			<uni-table ref="table" :loading="loading" border stripe emptyText="暂无更多数据"
				@selection-change="selectionChange">
				<uni-tr>
					<uni-th v-for="(item,index) in columns" :key="index">
						{{item.text}}
					</uni-th>
				</uni-tr>
				<uni-tr v-for="(row, index) in tableData" :key="index">
					<uni-td><view v-dictData="{type:'system_login_type',value:row.logType}"></view></uni-td>
					<uni-td>{{row.username}}</uni-td>
							<uni-td>{{row.userIp}}</uni-td>
							
							<uni-td>{{row.userAgent}}</uni-td>
							<uni-td><view v-dictData="{type:'system_login_result',value:row.result}"></view></uni-td>
							
					<uni-td>
						<uni-dateformat :date="row.createTime"></uni-dateformat>
					</uni-td>
								</uni-tr>
			</uni-table>
			<!-- 滚动到底提示 -->
			<uni-load-more :status="loadingData.content[loadingData.status]" :contentText="contentText"></uni-load-more>
		</scroll-view>
	</view>
</template>

<script>
	import {
		getLoginLogPageApi
	} from '@/api/work/log.js'
	import queryComponent from '@/components/queryComponent.vue'
	export default {
		components: {
			queryComponent
		},
		data() {
			return {
				// 表格加载
				loading: true,
				// 加载数据提示
				loadingData: {
					content: ["more", "loading", "noMore"],
					status: 0
				},
				contentText: {
					contentdown: '查看更多',
					contentrefresh: '加载中',
					contentnomore: '没有更多'
				},
				// 查询条件
				queryParams: {
					pageNo: 1,
					pageSize: 20
				},
				// 列信息
				columns: [
					    {
					         text: '日志类型',
					         value: 'logType'
					       },
					       {
					         text: '用户名称',
					         value: 'username',
					         isSearch: true
					       },
					       {
					         text: '登录地址',
					         value: 'userIp',
					         isSearch: true
					       },
					       {
					         text: '浏览器',
					         value: 'userAgent'
					       },
					       {
					         text: '登陆结果',
					         value: 'result'
					       },
					       {
					         text: '登录日期',
					         value: 'createTime',
					         formatter: 'formatDate'
					       }
				],
				tableData: []
			};
		},
		methods: {
			loadMore() {
				if (this.loadingData.status === 1 || this.loadingData.status === 2) {
					// 如果正在加载中或已经没有更多数据了，直接返回
					return
				}
				// 设置加载状态为加载中
				this.loadingData.status = 1;
				// 加载数据
				this.loading = true;
				this.queryParams.pageNo += 1;
				getLoginLogPageApi(this.queryParams).then(response => {
					// 如果没有数据了，设置加载状态为没有更多数据
					if (response.data.list.length === 0) {
						this.loadingData.status = 2;
					}else{
						this.loadingData.status = 0;
					}
					this.tableData = [...this.tableData,...response.data.list];
					this.loading = false;
				}).catch(error => {
					this.loading = false;
					this.loadingData.status = 0;
					this.$modal.showToast('加载失败！' + error.msg)
					this.queryParams.pageNo -= 1;
				});
			},
			// 获得表格信息
			 getData() {
				this.loading = true;
				getLoginLogPageApi(this.queryParams).then(response => {
					this.tableData = response.data.list;
					// 没有更多了
					if(response.data.list.length == response.data.total){
						this.loadingData.status = 2;
					}
				}).finally(() => {
					this.loading = false;
				});
			},
			// 搜索
			confirm() {
				this.getData()
			}
		},
		mounted() {
			this.getData();
		},
		async onPullDownRefresh() {
				await this.getData();
				setTimeout(function () {
					uni.stopPullDownRefresh();
				}, 400);
			}
	}
</script>

<style lang="scss">
	.scroll{
		height: calc(100vh - 1px);
	}
</style>