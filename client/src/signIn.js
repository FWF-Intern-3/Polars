import {checkPassword,checkEmail}from './check.js'
import {$}from './tool.js'
/**
 * @description 注册页面跳转
 */
const toSignUp = function(){
    window.location.href = '/template/signUp.html'
}


const run = (function(){
    let submit = $('signIn-submit');
    submit.addEventListener('click',()=>{signIn()});
    let toUp = $('signIn-toUp');
    toUp.addEventListener('click',()=>{toSignUp()})
})();
 

const signIn = function(){    
    let email = $('signIn-email').value;
    let password = $('signIn-password').value;
    if(checkEmail(email)&&checkPassword(password)){
        alert('客户端验证通过，向服务器提交邮箱密码进行验证！');
        // axios.post('',{email:email,password:password})
        // .then(
        //     (res)=>{
        //         if(res.code === 0){
        //             alert(res.msg);
        //             localStorage.setItem('token',res.data.token);
        //             window.location.href = '/tem'
        //         }
        //         else if(res.code === 1){
        //             alert(res.msg);
        //         }
        //     }
        // )
        // .catch(
        //     (err)=>{
        //         alert('服务器响应失败')
        //     }
        // )

        // 测试 axios
        axios.post('http://jsonplaceholder.typicode.com/posts',{'myname':'xjx'})
        .then(
            (res)=>{
                console.log(res)
            }
        )
        .catch(
            (err)=>{
                console.log(err)
            }
        )
    }
    else{
        alert('客户端验证失败')
    }
}
