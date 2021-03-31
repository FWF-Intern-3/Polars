import {checkPassword,checkEmail}from './check.js'
import {get}from './tool.js'

const run = (() => {
    //let submit = 
    $('#signIn-side2-submit').click(
        ()=>{signIn()}
    )
    //submit.addEventListener('click',()=>{signIn()});
})();
 
//export const storeEmail;

const signIn = () => {    
    let email = get('signIn-side2-inputEmail').value;
    let password = get('signIn-side2-inputPassword').value;
    if(checkEmail(email)&&checkPassword(password)){
        //storeEmail = email;
        alert('客户端验证通过，向服务器提交邮箱密码进行验证！');
        axios.post('',{email:email,password:password})
        .then(
            (res)=>{
                if(res.data.code === '0'){
                    alert(res.msg);
                    //localStorage.setItem('token',res.data.token);
                    window.location.href = '/template/chat.html'  //页面跳转
                    const userInfor = new Object();
                    userInfor.name = res.data.username;
                    localStorage.setItem('logInUser',JSON.stringify(userInfor));
                }
                else if(res.data.code === '1'){
                    alert(res.msg);
                }
            }
        )
        .catch(
            (err)=>{
                alert('服务器响应失败')
            }
        )
    }
    else{
        alert('客户端验证失败')
    }
}

document.onkeydown =  (e) => { // 回车提交表单
    var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        signIn();
    }
}