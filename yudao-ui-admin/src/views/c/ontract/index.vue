<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="system_user表用户ID" prop="userId">
        <el-input v-model="queryParams.userId" placeholder="请输入system_user表用户ID" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="用户的真实姓名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入用户的真实姓名" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="用户的身份证号" prop="identityCard">
        <el-input v-model="queryParams.identityCard" placeholder="请输入用户的身份证号" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="薪资" prop="salary">
        <el-input v-model="queryParams.salary" placeholder="请输入薪资" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="附件" prop="attach">
        <el-input v-model="queryParams.attach" placeholder="请输入附件" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="合同状态 0未签订 1签订 2到期 3终止" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择合同状态 0未签订 1签订 2到期 3终止" clearable size="small">
          <el-option v-for="dict in this.getDictDatas(DICT_TYPE.CONTRACT_STATUS)"
                       :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="甲方" prop="firstParty">
        <el-input v-model="queryParams.firstParty" placeholder="请输入甲方" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="签约时间" prop="signedTime">
        <el-date-picker v-model="queryParams.signedTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="合同开始时间" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="合同结束时间" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
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
                   v-hasPermi="['c:ontract:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['c:ontract:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="主键(自增策略)" align="center" prop="id" />
      <el-table-column label="system_user表用户ID" align="center" prop="userId" />
      <el-table-column label="用户的真实姓名" align="center" prop="name" />
      <el-table-column label="用户的身份证号" align="center" prop="identityCard" />
      <el-table-column label="薪资" align="center" prop="salary" />
      <el-table-column label="附件" align="center" prop="attach" />
      <el-table-column label="业绩要求" align="center" prop="performanceRequirements" />
      <el-table-column label="违约条款" align="center" prop="defaultClause" />
      <el-table-column label="合同状态 0未签订 1签订 2到期 3终止" align="center" prop="status">
        <template v-slot="scope">
          <dict-tag :type="DICT_TYPE.CONTRACT_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="甲方" align="center" prop="firstParty" />
      <el-table-column label="签约时间" align="center" prop="signedTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.signedTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="合同开始时间" align="center" prop="startTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="合同结束时间" align="center" prop="endTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['c:ontract:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['c:ontract:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="system_user表用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入system_user表用户ID" />
        </el-form-item>
        <el-form-item label="用户的真实姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入用户的真实姓名" />
        </el-form-item>
        <el-form-item label="用户的身份证号" prop="identityCard">
          <el-input v-model="form.identityCard" placeholder="请输入用户的身份证号" />
        </el-form-item>
        <el-form-item label="薪资" prop="salary">
          <el-input v-model="form.salary" placeholder="请输入薪资" />
        </el-form-item>
        <el-form-item label="附件" prop="attach">
          <el-input v-model="form.attach" placeholder="请输入附件" />
        </el-form-item>
        <el-form-item label="业绩要求" prop="performanceRequirements">
          <el-input v-model="form.performanceRequirements" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="违约条款" prop="defaultClause">
          <el-input v-model="form.defaultClause" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="合同状态 0未签订 1签订 2到期 3终止" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio v-for="dict in this.getDictDatas(DICT_TYPE.CONTRACT_STATUS)"
                      :key="dict.value" :label="dict.value">{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="甲方" prop="firstParty">
          <el-input v-model="form.firstParty" placeholder="请输入甲方" />
        </el-form-item>
        <el-form-item label="签约时间" prop="signedTime">
          <el-date-picker clearable v-model="form.signedTime" type="date" value-format="timestamp" placeholder="选择签约时间" />
        </el-form-item>
        <el-form-item label="合同开始时间" prop="startTime">
          <el-date-picker clearable v-model="form.startTime" type="date" value-format="timestamp" placeholder="选择合同开始时间" />
        </el-form-item>
        <el-form-item label="合同结束时间" prop="endTime">
          <el-date-picker clearable v-model="form.endTime" type="date" value-format="timestamp" placeholder="选择合同结束时间" />
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
import { createontract, updateontract, deleteontract, getontract, getontractPage, exportontractExcel } from "@/api/c/ontract";

export default {
  name: "ontract",
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
      // 合同表单列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        userId: null,
        name: null,
        identityCard: null,
        salary: null,
        attach: null,
        performanceRequirements: null,
        defaultClause: null,
        status: null,
        firstParty: null,
        signedTime: [],
        startTime: [],
        endTime: [],
        createTime: [],
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [{ required: true, message: "system_user表用户ID不能为空", trigger: "blur" }],
        name: [{ required: true, message: "用户的真实姓名不能为空", trigger: "blur" }],
        identityCard: [{ required: true, message: "用户的身份证号不能为空", trigger: "blur" }],
        salary: [{ required: true, message: "薪资不能为空", trigger: "blur" }],
        status: [{ required: true, message: "合同状态 0未签订 1签订 2到期 3终止不能为空", trigger: "blur" }],
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
      getontractPage(this.queryParams).then(response => {
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
        userId: undefined,
        name: undefined,
        identityCard: undefined,
        salary: undefined,
        attach: undefined,
        performanceRequirements: undefined,
        defaultClause: undefined,
        status: undefined,
        firstParty: undefined,
        signedTime: undefined,
        startTime: undefined,
        endTime: undefined,
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
      this.title = "添加合同表单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getontract(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改合同表单";
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
          updateontract(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createontract(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除合同表单编号为"' + id + '"的数据项?').then(function() {
          return deleteontract(id);
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
      this.$modal.confirm('是否确认导出所有合同表单数据项?').then(() => {
          this.exportLoading = true;
          return exportontractExcel(params);
        }).then(response => {
          this.$download.excel(response, '合同表单.xls');
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
