import {checkEmail,checkPassword,checkCaptch,checkUserName}from './check.js'
import {get}from './tool.js'

/**
 * @description 获取验证码
 * @param {string} myemail 
 */
const getCaptcha = (myemail) => {
    if(checkEmail(myemail)){
        axios.post('',{email:myemail})
        .then(
            (res)=>{
                if(res.data.code === '0'){
                    alert('验证码已发至邮箱！');
                }
                else if(res.data.code === '1'){ // 以具体后端接口而定
                    alert('该邮箱已被注册，请更换邮箱！');
                    return false;
                }
            }
        )
        .catch(
            (err)=>{
                console.log(err)
                alert('服务器响应失败！');
            }
        )
        alert('邮箱客户端检测通过，向服务器提交接收验证码的邮箱中。')
    }
}

/**
 * @description 注册功能主要逻辑
 */
const signUp = () => {
    let myemail = get('signUp-side2-inputEmail').value;
    let mycaptcha = get('signUp-side2-inputCaptcha').value;
    let password = get('signUp-side2-inputPassword').value;
    let password2 = get('signUp-side2-conPass').value;
    let userName = get('signUp-side2-inputUserName').value;

    if(checkEmail(myemail)&&checkCaptch(mycaptcha)&&checkPassword(password)&&checkUserName(userName)){
        if(password2 == ''){
            alert('请再次确认密码')
        }
        else if(password === password2){
            axios.post('',{email:myemail,'password':password,captcha:mycaptcha,username:userName})
            .then(
                (res)=>{
                    if(res.data.code === '0'){
                        alert('注册成功!');
                        //localStorage.setItem('token',res.token);
                        window.location.href = '/template/signIn.html' // 页面跳转 
                    }
                    else if(res.data.code === '1'){
                        alert('验证码错误')
                    }
                }
            )
            .catch(
                (err)=>{
                    alert('服务器响应失败')
                }
            )
            alert('注册信息填写无误，向服务器提交注册信息中！')
        }
        else if(password != password2){
            alert('两次输入密码不一致')
        }
    }
}

/**
 * @description 初始化，添加事件监听
 */
const run = (() => {
    $('#getCaptcha').click(
        ()=>{
        let myemail = get('signUp-side2-inputEmail').value;
        getCaptcha(myemail)
    }
    );
    $('#signUp-side2-submit').click(
        ()=>{signUp()}
    );
    $(document).ready(function(){
        $('.iconchenggong-01').fadeOut(),
        $('.signUp-side2-inputCaptcha').keydown(
            ()=>{
                let len = $('.signUp-side2-inputCaptcha').val().length;
                if(len === 5){
                    $('.iconchenggong-01').fadeIn(500)
                    $('.iconcuowushibaibukeyong').fadeOut(500)
                }
                else{
                    $('.iconchenggong-01').fadeOut();
                    $('.iconcuowushibaibukeyong').fadeIn(500)
                }
            }
        )
    }
    )
})();

document.onkeydown =  (e) => { // 回车提交表单
    var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        signUp();
    }
}
