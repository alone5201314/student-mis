import axios from 'axios';
import { Message } from 'element-ui';

axios.defaults.timeout = 5000; //超时终止请求
axios.defaults.baseURL ='http://localhost:8443/'; //配置请求地址


axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
axios.defaults.withCredentials = true;

// var loadingInstance
axios.interceptors.request.use(config => {
  //Ajax请求执行该方法，请求带上token
  var token = localStorage.getItem('userToken');
  const refreshToken = localStorage.getItem('refreshToken');
  if (token !== null && token !== undefined && token !== '') {
    config.headers.Authorization = token;
    var host = window.location.host;
    config.headers['appHost'] = host;
  }
  if (refreshToken !== null && refreshToken !== undefined && refreshToken !== '') {
    config.headers.freshToken = refreshToken;
  }
  //全局配置，get请求加时间戳
  if (config.method.toLowerCase() === "get") {
    config.url += config.url.match(/\?/) ? "&" : "?";
    config.url += "_dc=" + new Date().getTime();
  }
  // loadingInstance = Loading.service({ fullscreen: true })
  return config;
}, error => {  //请求错误处理
  // loadingInstance.close()
  Message.error({
    message: '加载超时'
  });
  return Promise.reject(error);
});
//
var count = 0;
axios.interceptors.response.use(response => {
    // loadingInstance.close()
    // 后端会在响应头里续期 token，前端同步更新本地身份信息。
    const token = response.headers.authorization;
    const refreshToken = response.headers.freshtoken;
    if (token) {
      localStorage.setItem('userToken', token);
    }
    if (refreshToken) {
      localStorage.setItem('refreshToken', refreshToken);
    }
    return response;
  },
  error => {
    if (error.response && error.response.status === 401) {// eslint-disable-next-line no-console
      if (count === 0) {
        count = count + 1;

      } else if (count > 0) {
        return null;
      }
      // debugger
      Message.error("身份信息超时，请重新登录！", {icon: 1, time: 2000});
      $cookies.remove('userToken');
      setTimeout(function () {
        //跳转到登录页
        // var host = window.location.host + '/login';
        // if (host.indexOf("http://") == -1) {
        //   host = 'http://' + host;
        // }
        // window.location.href = host;

        window.location.href = '/#/login';
      }, 3000);
    }
    return Promise.reject(error);
  });

export default axios; //暴露axios实例
