<template>
  <div>
    <el-card style="height:70px;width: 98%;margin: 10px 0 12px 12px;padding-top: 15px;box-sizing: border-box">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="专业：" prop="profession">
              <el-select v-model="form.professionObj" style="width: 90%" @change="professionChange" value-key="profession">
                <el-option v-for="item in classArr" :key="item.profession" :label="item.profession" :value="item"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="班级：" prop="grade">
              <el-select v-model="form.grade" style="width: 90%" @change="click">
                <el-option v-for="item in gradeArr" :key="item" :label="item" :value="item"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="课程：" prop="course">
              <el-select v-model="form.course" style="width: 90%" @change="courseChange">
                <el-option v-for="item in courseArr" :key="item" :label="item" :value="item"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <el-card style="height:430px;width: 98%;margin: 0 0 12px 12px;">
      <div style="margin: 15px 15px 30px 15px">
        <span style="font-size: 14px;color: #606266;">图表选择：</span>
        <el-select size="small" v-model="chartValue" @change="changeChart">
          <el-option v-for="item in chartArr" :key="item.value" :value="item.value" :label="item.label"></el-option>
        </el-select>
      </div>
      <div style="margin-top: -15px;height: 360px;" v-if="showChart">
        <!--饼状图-->
        <ve-pie v-if="chartValue === 1" :data="pieData" :extend="pieExtend"></ve-pie>
        <!--折线图-->
        <ve-line v-if="chartValue === 2" :data="lineData" :extend="lineExtend" ref="chart" :width="lineWidth" :height="lineHeight"></ve-line>
        <!--条形图-->
        <ve-histogram
          v-if="chartValue === 3"
          :data="histogramData"
          :extend="histogramExtend"
          ref="chart"
          :height="lineHeight">
        </ve-histogram>
      </div>
      <div style="text-align: center;line-height: 335px;" v-else>
        <span style="color: gray">暂无数据</span>
      </div>
    </el-card>
    <el-card class="info">
      <el-row>
        <el-col :span="6">最高分：{{total.max}}</el-col>
        <el-col :span="6">最低分：{{total.min}}</el-col>
        <el-col :span="6">平均分：{{total.average}}</el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "dashboard",
    data () {
      return {
        chartValue: 1,
        chartArr: [{
          label: '饼状图',
          value: 1
        }, {
          label: '折线图',
          value: 2
        }, {
          label: '柱形图',
          value: 3
        }],
        classArr: [],
        gradeArr: [],
        courseArr: [],
        form: {
          professionObj: {},
          grade: '',
          profession: '',
          course: ''
        },
        userInfo: {},
        showChart: true,
        lineHeight: '360px',
        lineWidth: '100%',
        pieData: {
          columns: ['label', 'value'],
          rows: [],
        },
        histogramData: {
          columns: ['realName', '分数'],
          rows: []
        },
        pieExtend: {
          color: ['#7BA7D9', '#8CC7BA', '#9AA7D8', '#E6B98A', '#B8C0CC', '#8FA6B8'],
          legend: {
            top: 10,
            textStyle: {
              color: '#5C667A'
            }
          },
          tooltip: {
            backgroundColor: 'rgba(255, 255, 255, 0.96)',
            borderColor: '#D8DFEA',
            borderWidth: 1,
            textStyle: {
              color: '#334155'
            },
            formatter: '{b}<br/>人数：{c} 人<br/>占比：{d}%'
          },
          series: {
            type: 'pie',
            radius: ['38%', '56%'],
            center: ['50%', '48%'],
            label: {
              color: '#5C667A',
              formatter: '{b}: {d}%'
            },
            labelLine: {
              lineStyle: {
                color: '#C9D2E3'
              }
            },
            itemStyle: {
              borderColor: '#F8FAFC',
              borderWidth: 2
            }
          }
        },
        lineExtend: {
          legend: {
            top: 10
          },
          grid: {
            left: '4%',
            right: '4%',
            bottom: '10%',
            containLabel: true
          },
          tooltip: {
            trigger: 'axis',
            backgroundColor: 'rgba(255, 255, 255, 0.96)',
            borderColor: '#D8DFEA',
            borderWidth: 1,
            textStyle: {
              color: '#334155'
            }
          },
          xAxis: {
            axisLabel: {
              interval: 'auto',
              rotate: 0,
              color: '#5C667A'
            },
            axisLine: {
              lineStyle: {
                color: '#D5DDEA'
              }
            }
          },
          yAxis: {
            min: 0,
            max: 100,
            splitLine: {
              lineStyle: {
                color: '#EDF1F7'
              }
            },
            axisLabel: {
              color: '#5C667A'
            }
          },
          dataZoom: [
            {
              type: 'inside',
              start: 0,
              end: 35,
              zoomLock: false
            },
            {
              type: 'slider',
              start: 0,
              end: 35,
              height: 18,
              bottom: 8,
              borderColor: 'transparent',
              backgroundColor: '#F2F6FC',
              fillerColor: 'rgba(123, 167, 217, 0.22)'
            }
          ],
          series: {
            type: 'line',
            smooth: true,
            symbol: 'circle',
            symbolSize: 6,
            lineStyle: {
              width: 2,
              color: '#6D8FB8'
            },
            itemStyle: {
              color: '#FFFFFF',
              borderColor: '#6D8FB8',
              borderWidth: 2
            },
            emphasis: {
              itemStyle: {
                color: '#6D8FB8',
                shadowBlur: 8,
                shadowColor: 'rgba(109, 143, 184, 0.25)'
              }
            },
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  {offset: 0, color: 'rgba(109, 143, 184, 0.20)'},
                  {offset: 1, color: 'rgba(109, 143, 184, 0.03)'}
                ]
              }
            },
            markLine: {
              silent: true,
              symbol: 'none',
              data: [{yAxis: 60, name: '及格线'}],
              lineStyle: {
                type: 'dashed',
                color: '#D29A5A'
              },
              label: {
                color: '#D29A5A',
                formatter: '及格线'
              }
            }
          }
        },
        histogramExtend: {
          legend: {
            top: 10
          },
          grid: {
            left: '4%',
            right: '4%',
            bottom: '10%',
            containLabel: true
          },
          tooltip: {
            backgroundColor: 'rgba(255, 255, 255, 0.96)',
            borderColor: '#D8DFEA',
            borderWidth: 1,
            textStyle: {
              color: '#334155'
            },
            formatter: function (params) {
              if (!params || !params.length) return '';
              const item = params[0];
              const label = item.axisValue || item.name;
              return label + '<br/>分数：' + item.value + ' 分';
            }
          },
          xAxis: {
            axisLabel: {
              interval: 0,
              rotate: 20,
              margin: 12,
              fontSize: 11,
              color: '#5C667A'
            },
            axisLine: {
              lineStyle: {
                color: '#D5DDEA'
              }
            }
          },
          yAxis: {
            min: 0,
            max: 100,
            splitLine: {
              lineStyle: {
                color: '#EDF1F7'
              }
            },
            axisLabel: {
              color: '#5C667A'
            }
          },
          dataZoom: [
            {
              type: 'inside',
              start: 0,
              end: 35,
              zoomLock: false
            },
            {
              type: 'slider',
              start: 0,
              end: 35,
              height: 18,
              bottom: 8,
              borderColor: 'transparent',
              backgroundColor: '#F2F6FC',
              fillerColor: 'rgba(64, 158, 255, 0.2)'
            }
          ],
          series: {
            type: 'bar',
            barMaxWidth: 28,
            itemStyle: {
              borderRadius: [6, 6, 0, 0],
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  {offset: 0, color: '#7BA7D9'},
                  {offset: 1, color: '#8CC7BA'}
                ]
              }
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 8,
                shadowColor: 'rgba(123, 167, 217, 0.25)'
              }
            },
            markLine: {
              silent: true,
              symbol: 'none',
              data: [{yAxis: 60, name: '及格线'}],
              lineStyle: {
                color: '#D29A5A',
                type: 'dashed'
              },
              label: {
                color: '#D29A5A',
                position: 'insideEndTop',
                formatter: '及格线'
              }
            }
          }
        },
        lineData: {
          columns: ['realName', '分数'],
          rows: []
        },
        total: {},
      }
    },
    methods: {
      getProfessionInfo () {
        this.axiosHelper.get(
          '/api/sms/teacher/course/getProfessionInfoByAdmin').then(response => {
          this.classArr = response.data;
          this.form = {
            profession: this.classArr[0].profession,
            grade: this.classArr[0].grade[0],
            professionObj: this.classArr[0],
            course: this.classArr[0].course[0],
          };
          this.gradeArr = this.classArr[0].grade;
          this.courseArr = this.classArr[0].course;
          this.getTotal();
          this.click();
        }).catch(error => {
          this.$message.error({
            message: '获取专业信息失败'
          }, error)
        });
      },
      professionChange (data) {
        this.showChart = false;
        this.form.grade = '';
        this.form.profession = data.profession;
        this.form.course = '';
        this.courseArr = data.course;
        this.gradeArr = data.grade;
      },
      changeChart () {
        this.click();
      },
      courseChange () {
        this.click();
      },
      click () {
        let userInfo = JSON.parse(localStorage.userInfo);
        let obj = {
          username: userInfo.username,
          level: userInfo.level,
          profession: this.form.profession || '',
          grade: this.form.grade || '',
          courseName: this.form.course || ''
        };
        this.getChartData(obj);
        this.getPieData(obj);
        this.getTotal();
      },
      getChartData (data) {
        this.axiosHelper.get(
          '/api/sms/score/export',
          {params: data}).then(response => {
          let data = response.data;
          this.lineData.rows = data.map(item => {
            let obj = {
              realName: item.realName,
              '分数': item.scoreByUser || 0
            };
            return obj
          });
          this.showChart = data.some(item => {
            return item.scoreByUser !== null
          });
          this.histogramData = this.lineData;
        }).catch(error => {
          this.$message.error({
            message: '获取成绩失败'
          }, error)
        })
      },
      getPieData (data) {
        this.axiosHelper.get(
          '/api/sms/score/getUserNum', {params: data}).then(
          response => {
            let data = response.data;
            this.pieData.rows = data;
            this.showChart = data.some(item => {
              return item.value !== 0;
            });
          }).catch(error => {
          this.$message.error({
            message: '获取饼图信息失败'
          }, error)
        })
      },
      getTotal () {
        let obj = {
          username: this.userInfo.username,
          profession: this.form.profession || '',
          grade: this.form.grade || '',
          courseName: this.form.course || '',
          level: this.userInfo.level
        };
        this.axiosHelper.get(
          '/api/sms/score/getUserTotal', {params: obj}).then(
          response => {
            let obj = response.data;
            let current = {
              max: this.dealTotal(obj.max),
              min: this.dealTotal(obj.min),
              average: this.dealTotal(obj.average)
            };
            this.total = {...current};
          })
      }
    },
    computed: {
      collapse() {
        return this.$store.state.collapse;
      }
    },
    watch: {
      collapse() {
        setTimeout(() => {
          this.$refs['chart'].resize()
        }, 150)
      }
    },
    mounted() {
      this.userInfo = JSON.parse(localStorage.userInfo);
      this.getProfessionInfo();
    },
  }
</script>

<style scoped>
  .content {
    height: 300px;
    float: left;
  }
  .content:hover {
    background-color: #F5FAF5;
    box-shadow: 5px 8px 6px 0px rgba(70,88,131,0.2);
  }
  .changeChartStyle {
    margin: -4px 0 0 97.5%;
    color: gray;
    font-size: 16px;
  }
  .changeChartStyle:hover {
    transform: scale(1.2);
    color: #409EFF;
  }
  .info {
    font-weight: bold;
    height: 80px;
    line-height: 80px;
    padding-left: 30px;
    box-sizing: border-box;
    width: 98%;
    margin: 0 0 0 12px;
  }
</style>
<style>
  #app , body {
    padding: 0;
    margin: 0;
  }
</style>
