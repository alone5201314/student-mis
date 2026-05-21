<template>
  <el-dialog v-dialogDrag :title="title" :visible.sync="dialog" :close-on-click-modal=false append-to-body width="820px">
    <el-alert
      title="课程由“课程录入”模块统一维护，本页面仅展示当前专业下的课程。"
      type="info"
      show-icon
      :closable="false"
      style="margin-bottom: 10px">
    </el-alert>
    <VmBaseTable
      v-if="dataTable.length > 0"
      :setTableHigh="true"
      ref="profession_course_table"
      :data="dataTable"
      :columns="dataColumns"
      overflow
      showIndex
      noPage
      tableHigh="420px"
    ></VmBaseTable>
    <div v-else class="empty-course">
      当前专业暂无课程，请前往“课程录入”新增课程并选择该专业。
    </div>
  </el-dialog>
</template>

<script>
  import VmBaseTable from '../../../base/base-table'

  export default {
    name: "ProfessionCourseModel",
    components: {
      VmBaseTable
    },
    data () {
      return {
        dialog: false,
        title: '课程列表',
        dataTable: [],
        dataColumns: [
          {
            label: '课程编号',
            prop: 'id',
            style: 'center',
            minWidth: '120'
          }, {
            label: '课程名称',
            prop: 'name',
            style: 'center',
            minWidth: '140'
          }, {
            label: '学分',
            prop: 'credits',
            style: 'center',
            minWidth: '70',
            render (h, params) {
              if (params.row.credits % 1 === 0) {
                return h('div', {}, `${params.row.credits}.0`)
              }
              return h('div', {}, params.row.credits)
            }
          }, {
            label: '课时',
            prop: 'number',
            style: 'center',
            minWidth: '70'
          }, {
            label: '类型',
            prop: 'type',
            style: 'center',
            minWidth: '80',
            render (h, params) {
              if (params.row.type === 1) {
                return h('div', {}, '必修')
              } else if (params.row.type === 2) {
                return h('div', {}, '选修')
              }
              return h('div', {}, '')
            }
          }, {
            label: '届时',
            prop: 'year',
            style: 'center',
            minWidth: '90',
            render (h, params) {
              return h('div', {}, `${params.row.year}届`)
            }
          }, {
            label: '学期',
            prop: 'term',
            style: 'center',
            minWidth: '90',
            render (h, params) {
              if (params.row.term === 1) {
                return h('div', {}, '上学期')
              } else if (params.row.term === 2) {
                return h('div', {}, '下学期')
              }
              return h('div', {}, '')
            }
          }
        ]
      }
    },
    methods: {
      init (profession) {
        this.title = `${profession.name} - 课程列表`;
        this.dialog = true;
        this.dataTable = [];
        this.axiosHelper.get('/api/sms/course/getCourseByMap', {
          params: {
            profession: profession.name
          }
        }).then(response => {
          this.dataTable = this.uniqueCourses(response.data);
        }).catch(error => {
          this.$message.error({
            message: '获取课程列表失败'
          }, error)
        })
      },
      uniqueCourses (courses) {
        const courseMap = {};
        courses.forEach(course => {
          if (!courseMap[course.id]) {
            courseMap[course.id] = course;
          }
        });
        return Object.keys(courseMap).map(id => courseMap[id]);
      }
    }
  }
</script>

<style scoped>
  .empty-course {
    height: 420px;
    border: 1px solid #ebeef5;
    border-radius: 4px;
    color: #909399;
    display: flex;
    align-items: center;
    justify-content: center;
  }
</style>
