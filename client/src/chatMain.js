import {addBub,addBubGroup}from './chatBubble.js'
import {addUser,openBox,openChatBoxEach,init,goFirst,userRecord} from './chatUserList.js'
import {storeFun}from './store.js'
import {cardsRun}from './Cards.js'
import {getNum,get,getClasses} from './tool.js'
import {reminder,judgeBox}from './msgReminder.js'

addUser('group','groupName','天理大学生<strong><del>摸鱼</del></strong>学习群!','/static/img/1616828173541.jpg')
addUser('Jamond','JamondName','这个人很懒，什么都没写！',`/static/img/Avatar${getNum()}.png`)
addUser('jinxi','jinxiName','这个人很懒，什么都没写！',`/static/img/Avatar${getNum()}.png`)

const socket = new WebSocket('wss://echo.websocket.org');
socket.onopen =(evt) => {
        alert('已建立连接！')
}

socket.onclose = (evt) => {
        alert('连接已断开！');
        window.location.href = '/template/signIn(new).html'
}

socket.onmessage =(evt) => {
        let val = JSON.parse(evt.data)
        console.log(val);
        mainGate(val);
}



export const userInfor = new Object();
// userInfor.name = prompt('你的名称（只支持英文）');
// userInfor.id = prompt('你的ID');
// localStorage.setItem('logInUser',JSON.stringify(userInfor));

// let getUserName = localStorage.getItem('userInfor');
// getUserName = JSON.parse(getUserName);
// userInfor.name = getUserName.name;
// localStorage.setItem('logInUser',JSON.stringify(userInfor));

let getUserName = localStorage.getItem('logInUser');
getUserName = JSON.parse(getUserName);
userInfor.name = getUserName.name;


/**
 * @description 发信息
 * @param {string} theCode 
 * @param {string} myname 
 * @param {string} friend 
 * @param {string} msg 
 */
const sendMsg = (theCode,myname,friend,msg) => {
    let val = new Object();
    if(theCode === '2'){
        val.code = '2';  // 2
        val.fromName = myname;
        val.toName = friend;
        val.data = `$${msg}`;  // 如果是我自己发出的消息 则 在消息中加上 $
    }else if(theCode === '1'){
        val.code = '1';
        val.fromName = myname;
        val.toName = null;
        val.data = `$${msg}`;
    }
    // 用于测试 实际上code 不可能 = 3！
    else if(theCode === '3'){
        val.code = '3';
        val.fromName = myname;
        val.toName = friend;
        val.data = `$${msg}`;
    }
    socket.send(JSON.stringify(val))
}



/**
 * @description 刷新用户列表
 * @param {object} mainData 
 */
const refreshUser = (mainData) => {
    let set = new Set();
    let obj = $('.user');
    for(let i = 0;i < obj.length; i++){
        set.add(obj[i].id)
    }
    for(let i = 0;i<mainData.data.length;i++){
        let temp = mainData.data[i];
        if(!set.has(temp)){
            addUser(temp,`${temp}Name`,'Hello World',`/static/img/Avatar${getNum()}.png`)
        }
    }
}

$('.sendMsg').click(
    () => {
        let val2 = get('textingBar-texting');
        if(userRecord.name != 'group'){
            let val = $('.textingBar-texting').val();
            sendMsg('2',userInfor.name,`${userRecord.name}`,`${val}`);
            val2.value = '';
        }else{
            let val = $('.textingBar-texting').val();
            sendMsg('1',userInfor.name,`${userRecord.name}`,`${val}`);
            val2.value = '';
        }
        // 视窗保持在底部
        //$('.msgBar').animate({scrollTop: $('.msgBar').height()}, 50);
    }
);

/**
 * @description 收到服务器消息进行判别分类
 * @param {object} mainData 
 */
const mainGate = (mainData) => {
    switch(mainData.code){
        case '1': // 群聊
            storeFun(mainData,'1');  // TODO: 处理群聊信息处理
            let res = mainData.fromName === userInfor.name?true:false;
            addBubGroup(mainData.data,mainData.fromName,null,res);
            reminder(mainData);  // 消息提醒
            break;
        case '2': // one by one
            storeFun(mainData,'2')  // TODO: 考虑把添加聊天气泡操作在这里储存后执行！！
            mainData.data = mainData.data.replace(mainData.data.charAt(0),''); // 将 $ 去掉
            addBub(mainData.data,null,null,true);
            break;
        case '3':
            storeFun(mainData,'3');
            addBub(mainData.data,null,null,false);
            reminder(mainData);
            break;
        case '4':
            refreshUser(mainData)
            break;
    }
}


/**
 * @description 初始化
 */
const initRin = (() => {
    let temp = getClasses('headImg');
    for(let i = 0;i<temp.length;i++){
        temp[i].addEventListener('click',(event) => {
            event.stopPropagation();
            $('#cardId').toggleClass('cardHide');
            $('#cardId').toggleClass('card');
            $('#chatBoxId').toggleClass('cardHide');
            $('#chatBoxId').toggleClass('chatBox');
            $('.cardHeadImg').attr('src',`${event.target.src}`);
            $('.partthree').html(`${event.target.nextSibling.lastChild.innerHTML}`)
            $('.parttwo').html(`${event.target.nextSibling.firstChild.firstChild.innerHTML}`)
        })
    }
    $('.iconqita').click(
        () => {
            $('#popCardTwoID').toggleClass('popCardTwo');
            $('#popCardTwoID').toggleClass('cardHide')
        }
    );
    setTimeout(() => {
        sendMsg('1','向兴强',null,'欢迎来到POLARS!');
        sendMsg('1','张凯龙',null,'欢迎来到POLARS!');
        sendMsg('1','杜宇轩',null,'欢迎来到POLARS!');
    },2000);
    $('.iconguanbi1').click(
        () => {
            $('#cardId').toggleClass('card');
            $('#cardId').toggleClass('cardHide');
            $('#chatBoxId').toggleClass('chatBox');
            $('#chatBoxId').toggleClass('cardHide')
        }
    )
    $('.icontianjia-copy').click(
        () => {
            addUser('Jamond','JamondName','这个人很懒，什么都没写！',`/static/img/Avatar${getNum()}.png`)
    addUser('jinxi','jinxiName','这个人很懒，什么都没写！',`/static/img/Avatar${getNum()}.png`)
        }
    )
})();

