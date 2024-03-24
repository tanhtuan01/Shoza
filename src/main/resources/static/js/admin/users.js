function closeUserModal() {
    document.getElementById("user-modal").style.display = 'none'
    document.getElementById("user-modal-add").style.display = 'none'
}

function changeRoleTmp(e, role, id) {
    var eClosetTr = e.closest('tr');
    eClosetTr.querySelector("#roleUser").textContent = role;
    eClosetTr.querySelector(".td-role .td-role-top .role-option").classList.toggle('block')
}

var idUser;
var newRole;

function confirmRole(e, id) {
    console.log('Confirm Role');
     console.log("user id: " + id)
     idUser = id
     
    var node = e.parentNode.parentNode;
    document.getElementById("user-modal").style.display = 'block'
    var userName = node.querySelector(".tennguoidung").textContent;
    var role = node.querySelector("#roleUser").textContent;
    console.log("new role: " + role)
    newRole = role
    document.querySelector(".modal-content-message").textContent = `Thay đổi quyền của: '${userName}' thành '${role}'`
}

function roleOption(e) {
    // body...
    console.log(e.closest('tr'))
    var eClosetTr = e.closest('tr');
    eClosetTr.querySelector(".td-role .td-role-top .role-option").classList.toggle('block')
}

function openModalAddUser(e) {
    document.getElementById("user-modal-add").style.display = 'block'
}

function confirmChangeRole(e){
	event.preventDefault()
	var xmlhttp = new XMLHttpRequest()
	xmlhttp.onreadystatechange = function(){
		if(this.readyState == 4){
			if(this.status == 200){
				toast({title: 'Cập nhật', type:'success', message: 'Cập nhật quyền thành công', duration:'2500'})
			}
		}
	}


	xmlhttp.open('GET', `${portUrl}/admin/users/updateRole?role=${newRole}&id=${idUser}`, true)
	xmlhttp.send();
	closeUserModal();
}



async function checkNewUser(e){
	event.preventDefault()
	
	var formNode = e.closest("form")
	
	var userName = formNode.querySelector("#userName").value;
	console.log(userName)
	
	var email = formNode.querySelector("#email").value;
	console.log(email)
	
	var passwords = formNode.querySelector("#passwords").value;
	console.log(passwords)
	
	var role = formNode.querySelector("#role").value;
	console.log(role)
	
	console.log(`${portUrl}`)
	
	const check = await checkEmailBeforeAddUser(email, 0)
	
	if(check){
		toast({title:"Thêm mới người dùng",type:"warning",message:"Email đã tồn tại, hãy chọn email khác",duration:2500})
	}else{
		var uhref = `${portUrl}/admin/users/add?n=${userName}&e=${email}&p=${passwords}&r=${role}`
		console.log(uhref)
		toast({title:"Thêm mới người dùng",type:"success",message:`Thêm người dùng thành công ( ${userName} - ${role} )`,duration:2500})
		setTimeout(function(){
			window.location.href = uhref
		}, 2700)
	}
}

async function checkEmailBeforeAddUser(email, id){
	 const response = await fetch(`${portUrl}/admin/users/check-email?email=${email}&id=${id}`);
	  const data = await response.json();
	  return data;
}