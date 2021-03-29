import {addBub,addBubGroup}from './chatBubble.js'
import {addUser,openBox,openChatBoxEach,init,goFirst,userRecord} from './chatUserList.js'
import {storeFun}from './store.js'
import {cardsRun}from './Cards.js'
import {getNum,get} from './tool.js'
import {reminder,judgeBox}from './msgReminder.js'

addUser('group','groupName','没事儿，就想好好<del>摸鱼</del>学习!','/static/img/1616828173541.jpg')
addUser('Jamond','JamondName','这个人很懒，什么都没写！',`/static/img/Avatar${getNum()}.png`)

const socket = new WebSocket('wss://echo.websocket.org');
socket.onopen =(evt) => {
        alert('已建立连接！')
}

socket.onclose = (evt) => {
        alert('连接已断开！')
}

socket.onmessage =(evt) => {
        let val = JSON.parse(evt.data)
        console.log(val);
        mainGate(val);
}



const userInfor = new Object();
userInfor.name = prompt('你的名称（只支持英文）');
userInfor.id = prompt('你的ID');
localStorage.setItem('logInUser',JSON.stringify(userInfor));

// let getUserName = localStorage.getItem('userInfor');
// getUserName = JSON.parse(getUserName);
// userInfor.name = getUserName.name;


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
        val.code = '1';
        val.fromName = myname;
        val.toName = null;
        val.data = `${msg}`;
    }
    socket.send(JSON.stringify(val))
}


const refreshUser = (mainData) => {
    for(let i = 0;i<mainData.data.length;i++){
        let temp = mainData.data[i];
        addUser(temp,`${temp}Name`,'Hello World',`/static/img/Avatar${getNum()}.png`)
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
        $('.RChatBar').animate({scrollTop: $('.RChatBar').height()}, 50);
    }
);

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


// addBubGroup('测试一下群聊消息,左边，左边，左边！','jamond','null',false)


// $('.user').click((event) => {
//     alert(event.target.id)
// })


// $('.user').click(
//     () => {
//         // if(classLen === 4&&judgeBox()){
//         //     $('')
//         // }
//         let temp = $('.user').html();
//         alert(temp)
//     }
// )


$('.icontianjia-copy').click(
    () => {
        sendMsg('3','Jamond',null,'测试下')
    }
)
