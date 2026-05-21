<template>
  <div>
    <el-card style="height:70px;width: 98%;margin: 10px 0 12px 12px;padding-top: 15px;box-sizing: border-box">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="学年：">
              <el-select v-model="form.year" style="width: 90%" @change="click">
                <el-option v-for="item in yearArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="学期：">
              <el-select v-model="form.term" style="width: 90%" @change="click">
                <el-option v-for="item in termArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
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
      <el-row style="">
        <el-col :span="6">总绩点：{{total.point}}</el-col>
        <el-col :span="6">总学分：{{total.credits}}</el-col>
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
        yearArr: [],
        form: {
          term: '',
          year: ''
        },
        termArr: [{
          label: '上',
          value: 1
        },{
          label: '下',
          value: 2
        }],
        total: {
          credits: '',
          point: ''
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
          columns: ['courseName', '分数'],
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
          columns: ['courseName', '分数'],
          rows: []
        },
      }
    },
    methods: {
      getYear () {
        let now = new Date().getFullYear();
        let old = parseInt(this.userInfo.admissionTime);
        // 扩展学年范围，包含从入学年份到当前年份+4年
        for (let i = old; i <= now + 4; i++) {
          let num = i - old;
          let label = '';
          if (num >= 0 && num < 4) {
            switch (num) {
              case 0:
                label = '大一';
                break;
              case 1:
                label = '大二';
                break;
              case 2:
                label = '大三';
                break;
              case 3:
                label = '大四';
                break;
            }
          } else if (num >= 4) {
            label = '大' + (num + 1) + '（延毕）';
          } else {
            label = '预入学';
          }
          this.yearArr.push({label: label + ' (' + i + '年)', value: i});
        }
        this.getDefault();
      },
      changeChart () {
        this.click();
      },
      getDefault () {
        // 获取学年和学期的初始值
        // 默认选择当前年份或入学年份中的较大值
        let now = new Date().getFullYear();
        let old = parseInt(this.userInfo.admissionTime);
        this.form.year = Math.max(old, now);
        let month = new Date().getMonth()+1;
        if (month > 2 && month < 6) {
          // 上学期
          this.form.term = this.termArr[0].value;
        } else {
          // 下学期
          this.form.term = this.termArr[1].value;
        }
        this.click();
      },
      click () {
        let userInfo = JSON.parse(localStorage.userInfo);
        let obj = {
          studentName: userInfo.username,
          level: userInfo.level,
          year: this.form.year || '',
          term: this.form.term || ''
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
              courseName: item.name,
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
            data = data.filter(item => {
              return item.label !== '未录入'
            });
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
      // 获取总学分和总绩点
      getTotal () {
        let obj = {
          studentName: this.userInfo.id,
          level: this.userInfo.level,
          year: this.form.year || '',
          term: this.form.term || ''
        };
        this.axiosHelper.get(
          '/api/sms/score/getUserTotal', {params: obj}).then(
          response => {
            let obj = response.data;
            let current = {
              credits: this.dealTotal(obj.credits),
              point: this.dealTotal(obj.point)
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
      this.getYear();
      this.getTotal();
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
