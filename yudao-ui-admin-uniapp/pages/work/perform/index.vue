<template>
	<view>
		<!-- 搜索栏 -->
		<query-component :queryParams="queryParams" @update:queryParams="queryParams = $event" :columns="columns" @confirm="confirm" />
		<scroll-view class="scroll" scroll-y @scrolltolower="loadMore()">

			<!-- 表格显示内容 -->
			<uni-table ref="table" :loading="loading" border stripe emptyText="暂无更多数据"
				>
				<uni-tr>
					<uni-th v-for="(item,index) in columns" :key="index">
						{{item.text}}
					</uni-th>
				</uni-tr>
				<uni-tr v-for="(row, index) in tableData" :key="index">
					<uni-td>
							<view v-name="row.userId"></view>
					</uni-td>
						<uni-td><view v-dept="row.deptId"></view></uni-td>
						<uni-td>
							<view v-processDefiend="row.processDefitionId"></view>
						</uni-td>
					<uni-td>
						{{row.startTime}}
					</uni-td>
					<uni-td>
						{{row.endTime?row.endTime:'不限时'}}
					</uni-td>
					<uni-td>
						{{row.notifyTime?row.notifyTime:'不限时'}}
					</uni-td>
					<uni-td>
						{{row.urgeTime?row.urgeTime:'不限时'}}
					</uni-td>
					<uni-td>
						<uni-dateformat :date="row.createTime"></uni-dateformat>
					</uni-td>
					<uni-td><button size="mini" @click="viewContract(row.contractId)">查看</button></uni-td>

				</uni-tr>
			</uni-table>
			<!-- 滚动到底提示 -->
			<uni-load-more :status="loadingData.content[loadingData.status]" :contentText="contentText"></uni-load-more>
		</scroll-view>
	</view>
</template>

<script>
	import {
		getPerformanceReportRequestPageApi
	} from '@/api/work/perform.js'
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
					         text: '用户',
					         value: 'userId'
					       },
					       {
					         text: '部门',
					         value: 'deptId',
							 },{
					         text: '流程定义',
					         value: 'processDefitionId'
					       },
					       {
					         text: '提交开始开始时间',
					         value: 'startTime'
					       },
					       {
					         text: '提交终止时间',
					         value: 'endTime'
					       },
					       {
					         text: '业绩提交通知时间',
					         value: 'notifyTime'
					       },
					       {
					         text: '未交提醒时间',
					         value: 'urgeTime'
					       },
					       {
					         text: '创建时间',
					         value: 'createTime'
					       },
						   {
						     text: '合同',
						     value: 'contractId'
						   }
				],
				tableData: []
			};
		},
		methods: {
			viewProcessDefined(id){
				// todo
			},
			viewContract(id){
				this.$tab.navigateTo('/pages/work/contract/detail?id='+id)
			},
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
				getPerformanceReportRequestPageApi(this.queryParams).then(response => {
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
				getPerformanceReportRequestPageApi(this.queryParams).then(response => {
					this.tableData = response.data.list;
					this.loading = false;
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