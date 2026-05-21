<template>
  <el-dialog v-dialogDrag :title="title" :visible.sync="dialog" :close-on-click-modal=false append-to-body width="420px">
    <el-form ref="form" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="专业名称:" prop="name">
        <el-input v-model="form.name" maxlength="30" clearable></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer">
      <el-button type="primary" @click="click('form')" size="small">确定</el-button>
      <el-button @click="cancel" size="small">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "ProfessionModel",
    data () {
      return {
        doneNum: 0,
        dialog: false,
        title: '',
        type: '',
        form: {
          id: '',
          name: ''
        },
        rules: {
          name: [
            { required: true, message: '专业名称不能为空', trigger: 'blur' },
            { min: 2, max: 30, message: '专业名称长度应在 2 到 30 个字符之间', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (obj) {
        this.clearForm();
        this.type = obj.type;
        if (obj.type === 'add') {
          this.title = '新增专业';
        } else if (obj.type === 'edit') {
          this.title = '修改专业';
          this.form = obj.row;
        }
        this.dialog = true;
      },
      clearForm () {
        this.doneNum = 0;
        this.type = '';
        this.form = {
          id: '',
          name: ''
        };
        if (this.$refs['form'] !== undefined) {
          this.$refs['form'].clearValidate();
        }
      },
      click (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.form.name = this.form.name.trim();
            if (this.type === 'add') {
              this.addMethod();
            } else if (this.type === 'edit') {
              this.editMethod();
            }
          }
        })
      },
      addMethod () {
        ++this.doneNum;
        if (this.doneNum === 1) {
          this.axiosHelper.post('/api/sms/profession', this.form).then(() => {
            this.doneNum = 0;
            this.$message.success({
              message: '新增成功'
            });
            this.dialog = false;
            this.$emit('search')
          }).catch(error => {
            this.doneNum = 0;
            this.showError(error, '新增失败');
          });
        }
      },
      editMethod () {
        this.axiosHelper.put('/api/sms/profession', this.form).then(() => {
          this.$message.success({
            message: '修改成功'
          });
          this.dialog = false;
          this.$emit('search')
        }).catch(error => {
          this.showError(error, '修改失败');
        });
      },
      showError (error, defaultMessage) {
        const data = error.response && error.response.data;
        this.$message.error({
          message: (data && data.message) || defaultMessage
        });
      },
      cancel () {
        this.dialog = false;
      }
    }
  }
</script>

<style scoped>
</style>
