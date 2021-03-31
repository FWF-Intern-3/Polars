import {checkEmail,checkPassword,checkCaptch,checkUserName}from './check.js'
import {get,$}from './tool.js'
/**
 * @description 注册
 * 检测邮箱 检测密码 确认密码一致 提交注册信息
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

const run = (() => {
    let getCaptchaVal = $('getCaptcha');
    let toSignUp = $('signUp-side2-submit');
    getCaptchaVal.addEventListener('click',()=>{
        let myemail = get('signUp-side2-inputEmail').value;
        getCaptcha(myemail)
    });
    toSignUp.addEventListener('click',()=>{signUp()})
})();

document.onkeydown =  (e) => { // 回车提交表单
    var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        signUp();
    }
}

/**
 * $  老是报错！ 换成 jQuery 就好
 */
jQuery(document).ready(function(){
    jQuery('.iconchenggong-01').fadeOut(),
    jQuery('.signUp-side2-inputCaptcha').keydown(
        ()=>{
            let len = jQuery('.signUp-side2-inputCaptcha').val().length;
            if(len === 5){
                jQuery('.iconchenggong-01').fadeIn(500)
                jQuery('.iconcuowushibaibukeyong').fadeOut(500)
            }
            else{
                jQuery('.iconchenggong-01').fadeOut();
                jQuery('.iconcuowushibaibukeyong').fadeIn(500)
            }
        }
    )
}
)