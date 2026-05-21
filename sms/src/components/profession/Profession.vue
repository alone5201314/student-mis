<template>
  <el-card class="responsive-card" style="margin: 10px;padding: 15px 10px 10px 10px">
    <el-row class="responsive-toolbar">
      <el-button type="primary" size="small" @click="addMethod">新增</el-button>
      <el-button type="danger" size="small" @click="deleteSelect">删除</el-button>
      <el-input
        class="toolbar-search"
        size="small"
        v-model="searchValue.code"
        placeholder="请输入专业编号或名称"
        clearable
        @keyup.enter.native="filterData">
        <el-button slot="append" @click="filterData" type="primary">过滤</el-button>
      </el-input>
    </el-row>
    <VmBaseTable
      :setTableHigh="true"
      @on-select-change="select"
      ref="profession_table"
      :data="dataTable"
      :columns="dataColumns"
      overflow
      showCheck
      showIndex
      noPage
      :tableHigh="tableHigh"
    ></VmBaseTable>
    <VmProfession ref="profession_model" @search="search"></VmProfession>
    <VmProfessionCourse ref="profession_course_model"></VmProfessionCourse>
  </el-card>
</template>

<script>
  import VmBaseTable from '../../base/base-table'
  import VmProfession from './model/ProfessionModel'
  import VmProfessionCourse from './model/ProfessionCourseModel'

  export default {
    name: "Profession",
    components: {
      VmBaseTable, VmProfession, VmProfessionCourse
    },
    data () {
      return {
        table: null,
        searchValue: {
          code: ''
        },
        tableHigh: '66vh',
        dataSource: [],
        dataTable: [],
        dataColumns: [
          {
            label: '专业编号',
            prop: 'id',
            style: 'center',
            minWidth: '120'
          }, {
            label: '专业名称',
            prop: 'name',
            style: 'center',
            minWidth: '180'
          }, {
            label: '课程数量',
            prop: 'courseCount',
            style: 'center',
            minWidth: '100',
            render (h, params) {
              return h('div', {}, params.row.courseCount || 0)
            }
          }, {
            label: '操作',
            style: 'center',
            minWidth: '220',
            render: (h, params) => {
              let btns = [];
              btns.push(this.getOpBtn(h, '查看课程', 'success', () => {
                this.courseMethod(params.row)
              }));
              btns.push(this.getOpBtn(h, '编辑', 'primary', () => {
                this.editMethod(params.row)
              }));
              btns.push(this.getOpBtn(h, '删除', 'danger', () => {
                this.deleteSingle(params.row)
              }));
              return h('div', btns)
            }
          }
        ]
      }
    },
    methods: {
      search () {
        this.axiosHelper.get('/api/sms/profession/getProfessionList').then(response => {
          this.dataSource = response.data;
          this.filterData();
        }).catch(error => {
          this.$message.error({
            message: '获取专业信息失败'
          }, error)
        })
      },
      filterData () {
        const code = (this.searchValue.code || '').trim();
        if (code === '') {
          this.dataTable = this.dataSource;
          return;
        }
        this.dataTable = this.dataSource.filter(item => {
          return item.name.indexOf(code) !== -1 || String(item.id).indexOf(code) !== -1
        });
      },
      addMethod () {
        this.$refs['profession_model'].init({
          type: 'add'
        });
      },
      editMethod (row) {
        this.$refs['profession_model'].init({
          type: 'edit',
          row: Object.assign({}, row)
        });
      },
      courseMethod (row) {
        this.$refs['profession_course_model'].init(Object.assign({}, row));
      },
      select () {
      },
      deleteSingle (obj) {
        this.deleteTable([obj.id]);
      },
      deleteSelect () {
        let ids = this.table.getIds();
        if (ids.length > 0) {
          this.deleteTable(ids);
        }
      },
      deleteTable (ids) {
        this.$confirm('删除专业前请确认没有学生、课程、任课或课表正在使用该专业，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteMethod(ids);
        }).catch(() => {
          this.$message.info({
            message: '已取消删除'
          });
        });
      },
      deleteMethod (ids) {
        this.axiosHelper.delete('/api/sms/profession/' + ids).then(() => {
          this.$message.success({
            message: '删除成功'
          });
          this.search();
        }).catch(error => {
          const data = error.response && error.response.data;
          this.$message.error({
            message: (data && data.message) || '该专业已有业务数据，不能删除'
          });
        })
      }
    },
    mounted () {
      this.table = this.$refs['profession_table'];
      this.search();
    }
  }
</script>
