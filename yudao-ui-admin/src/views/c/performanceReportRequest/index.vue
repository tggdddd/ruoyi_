<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关联的合同表ID" prop="contractId">
        <el-select v-model="queryParams.contractId" placeholder="请选择关联的合同表ID" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="业绩表单提交开始开始时间" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="业绩表单提交终止时间" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="业绩表单提交通知时间" prop="notifyTime">
        <el-date-picker v-model="queryParams.notifyTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="业绩提交流程id" prop="processDefitionId">
        <el-select v-model="queryParams.processDefitionId" placeholder="请选择业绩提交流程id" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="业绩表单未交通知时间" prop="urgeTime">
        <el-date-picker v-model="queryParams.urgeTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="用户id" prop="userId">
        <el-select v-model="queryParams.userId" placeholder="请选择用户id" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请的流程id" prop="processInstanceId">
        <el-select v-model="queryParams.processInstanceId" placeholder="请选择申请的流程id" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['c:performance-report-request:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['c:performance-report-request:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="主键(自增策略)" align="center" prop="id" />
      <el-table-column label="关联的合同表ID" align="center" prop="contractId" />
      <el-table-column label="业绩表单提交开始开始时间" align="center" prop="startTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="业绩表单提交终止时间" align="center" prop="endTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="业绩表单提交通知时间" align="center" prop="notifyTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.notifyTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="业绩提交流程id" align="center" prop="processDefitionId" />
      <el-table-column label="业绩表单未交通知时间" align="center" prop="urgeTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.urgeTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户id" align="center" prop="userId" />
      <el-table-column label="申请的流程id" align="center" prop="processInstanceId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['c:performance-report-request:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['c:performance-report-request:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="关联的合同表ID" prop="contractId">
          <el-select v-model="form.contractId" placeholder="请选择关联的合同表ID">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="业绩表单提交开始开始时间" prop="startTime">
          <el-date-picker clearable v-model="form.startTime" type="date" value-format="timestamp" placeholder="选择业绩表单提交开始开始时间" />
        </el-form-item>
        <el-form-item label="业绩表单提交终止时间" prop="endTime">
          <el-date-picker clearable v-model="form.endTime" type="date" value-format="timestamp" placeholder="选择业绩表单提交终止时间" />
        </el-form-item>
        <el-form-item label="业绩表单提交通知时间" prop="notifyTime">
          <el-date-picker clearable v-model="form.notifyTime" type="date" value-format="timestamp" placeholder="选择业绩表单提交通知时间" />
        </el-form-item>
        <el-form-item label="业绩提交流程id" prop="processDefitionId">
          <el-select v-model="form.processDefitionId" placeholder="请选择业绩提交流程id">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="业绩表单未交通知时间" prop="urgeTime">
          <el-date-picker clearable v-model="form.urgeTime" type="date" value-format="timestamp" placeholder="选择业绩表单未交通知时间" />
        </el-form-item>
        <el-form-item label="用户id" prop="userId">
          <el-select v-model="form.userId" placeholder="请选择用户id">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="申请的流程id" prop="processInstanceId">
          <el-select v-model="form.processInstanceId" placeholder="请选择申请的流程id">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { createPerformanceReportRequest, updatePerformanceReportRequest, deletePerformanceReportRequest, getPerformanceReportRequest, getPerformanceReportRequestPage, exportPerformanceReportRequestExcel } from "@/api/c/performanceReportRequest";

export default {
  name: "PerformanceReportRequest",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 业绩定义列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        contractId: null,
        startTime: [],
        endTime: [],
        notifyTime: [],
        createTime: [],
        processDefitionId: null,
        urgeTime: [],
        userId: null,
        processInstanceId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        contractId: [{ required: true, message: "关联的合同表ID不能为空", trigger: "change" }],
        startTime: [{ required: true, message: "业绩表单提交开始开始时间不能为空", trigger: "blur" }],
        processDefitionId: [{ required: true, message: "业绩提交流程id不能为空", trigger: "change" }],
        userId: [{ required: true, message: "用户id不能为空", trigger: "change" }],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 执行查询
      getPerformanceReportRequestPage(this.queryParams).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: undefined,
        contractId: undefined,
        startTime: undefined,
        endTime: undefined,
        notifyTime: undefined,
        processDefitionId: undefined,
        urgeTime: undefined,
        userId: undefined,
        processInstanceId: undefined,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加业绩定义";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getPerformanceReportRequest(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改业绩定义";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 修改的提交
        if (this.form.id != null) {
          updatePerformanceReportRequest(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createPerformanceReportRequest(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除业绩定义编号为"' + id + '"的数据项?').then(function() {
          return deletePerformanceReportRequest(id);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      // 处理查询参数
      let params = {...this.queryParams};
      params.pageNo = undefined;
      params.pageSize = undefined;
      this.$modal.confirm('是否确认导出所有业绩定义数据项?').then(() => {
          this.exportLoading = true;
          return exportPerformanceReportRequestExcel(params);
        }).then(response => {
          this.$download.excel(response, '业绩定义.xls');
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
