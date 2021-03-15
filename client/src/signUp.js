import {checkEmail,checkPassword,checkCaptch}from './check.js'
import {$}from './tool.js'
/**
 * @description 注册
 * 检测邮箱 检测密码 确认密码一致 提交注册信息
 */

const getCaptcha = function(email){
    if(checkEmail(email)){
        // axios.post('',{'email':email})
        // .then(
        //     (res)=>{
        //         if(res.code === 0){
        //             alert('验证码已发至邮箱！');
        //         }
        //         else if(res.code === 1){ // 以具体后端接口而定
        //             alert('该邮箱已被注册，请更换邮箱！');
        //             return false;
        //         }
        //     }
        // )
        // .catch(
        //     (err)=>{
        //         console.log(err)
        //         alert('服务器响应失败！');
        //     }
        // )
        alert('邮箱客户端检测通过，向服务器提交接收验证码的邮箱中。')
    }
    else{
        alert('000')
    } 
}

const signUp = function(){
    let email = $('signUp-email').value;
    let captch = $('signUp-captcha').value;
    let password = $('signUp-password').value;
    let password2 = $('signUp-conPassword').value;
    if(checkEmail(email)&&checkPassword(password)&&checkCaptch(captch)){
        if(password2 == ''){
            alert('请再次确认密码')
        }
        else if(password === password2){
            // axios.post('',{'email':email,'password':password,'captch':captch})
            // .then(
            //     (res)=>{
            //         if(res.code === 0){
            //             alert('注册成功!');
            //             localStorage.setItem('token',res.token);
            //             window.location.href = '  ' // 页面跳转 
            //         }
            //         else if(res.code === 1){
            //             alert('验证码错误')
            //         }
            //     }
            // )
            alert('注册信息填写无误，向服务器提交注册信息中！')
        }
        else if(password != password2){
            alert('两次输入密码不一致')
        }
    }
}

const run = (function(){
    let getCaptchaVal = $('signUp-getCaptcha');
    let toSignUp = $('signUp-submit');
    getCaptchaVal.addEventListener('click',()=>{
        let email = $('signUp-email').value;
        getCaptcha(email)
    });
    toSignUp.addEventListener('click',()=>{signUp()})
})();