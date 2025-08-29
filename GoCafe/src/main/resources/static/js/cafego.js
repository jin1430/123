/* =========================================
   CafeGo – Header Auth + UI interactions
   ========================================= */

// ----- Token helpers -----
function setToken(token){
  if(!token){ localStorage.removeItem('cafego_token'); return; }
  localStorage.setItem('cafego_token', token);
}
function getToken(){ return localStorage.getItem('cafego_token') || null; }
function getAuthHeaders(){ const t=getToken(); return t?{Authorization:`Bearer ${t}`}:{ }; }

// ----- Header elements -----
const loginFormHeader = document.getElementById('loginFormHeader');
const logoutBtnHeader = document.getElementById('logoutBtnHeader');
const meEmailHeader   = document.getElementById('meEmailHeader');
const authOut         = document.querySelector('.cg-auth-out');
const authIn          = document.querySelector('.cg-auth-in');

// (본문에 과거 폼이 남아있어도 안전하게)
const loginFormBody   = document.getElementById('loginForm');
const logoutBtnBody   = document.getElementById('logoutBtn');
const meBoxBody       = document.getElementById('meBox');
const meEmailBody     = document.getElementById('meEmail');

// ----- Me fetch / toggle -----
async function fetchMe(){
  try{
    const res = await fetch('/api/auth/me', { headers:{ ...getAuthHeaders() }});
    if(res.ok){
      const me = await res.json();
      if(meEmailHeader) meEmailHeader.textContent = me.memberEmail || '';
      if(authOut) authOut.style.display='none';
      if(authIn)  authIn.hidden=false;
      if(meBoxBody) meBoxBody.hidden=false;
      if(meEmailBody) meEmailBody.textContent = me.memberEmail || '';
    }else{
      if(authOut) authOut.style.display='';
      if(authIn)  authIn.hidden=true;
      if(meBoxBody) meBoxBody.hidden=true;
    }
  }catch(e){
    if(authOut) authOut.style.display='';
    if(authIn)  authIn.hidden=true;
    if(meBoxBody) meBoxBody.hidden=true;
  }
}

// ----- Login -----
async function handleLogin(form){
  const fd = new FormData(form);
  const payload = { memberEmail: fd.get('memberEmail'), memberPassword: fd.get('memberPassword') };
  const res = await fetch('/api/auth/login', {
    method:'POST', headers:{ 'Content-Type':'application/json' }, body:JSON.stringify(payload)
  });
  if(res.ok){
    const data = await res.json(); // {tokenType:"Bearer", token:"..."}
    setToken(data.token);
    await fetchMe();
    const details = form.closest('details'); if(details) details.open=false;
  }else{
    const err = await res.json().catch(()=>({message:'로그인 실패'}));
    alert(err.message || '로그인 실패');
  }
}
if(loginFormHeader){ loginFormHeader.addEventListener('submit', e=>{ e.preventDefault(); handleLogin(loginFormHeader); }); }
if(loginFormBody){   loginFormBody.addEventListener('submit',   e=>{ e.preventDefault(); handleLogin(loginFormBody);   }); }

// ----- Logout -----
async function handleLogout(){
  try{ await fetch('/api/auth/logout', { method:'POST', headers:{...getAuthHeaders()} }); }catch(_){}
  setToken(null);
  await fetchMe();
}
if(logoutBtnHeader){ logoutBtnHeader.addEventListener('click', handleLogout); }
if(logoutBtnBody){   logoutBtnBody.addEventListener('click',   handleLogout); }

// ----- Chips -> /search -----
document.querySelectorAll('.cg-chip').forEach(chip=>{
  chip.addEventListener('click', ()=>{
    const tag = chip.dataset.tag, cat = chip.dataset.category;
    const url = new URL(location.origin + '/search');
    if(tag) url.searchParams.set('tag', tag);
    if(cat) url.searchParams.set('category', cat);
    location.href = url.toString();
  });
});

// init
fetchMe();

// ===== Auth pages: login & signup =====
const loginPageForm  = document.getElementById('loginPageForm');
const signupPageForm = document.getElementById('signupPageForm');

// loginPageForm 제출 시
if (loginPageForm){
  loginPageForm.addEventListener('submit', async (e)=>{
    e.preventDefault();
    const fd = new FormData(loginPageForm);
    const payload = { memberEmail: fd.get('memberEmail'), memberPassword: fd.get('memberPassword') };
    const res = await fetch('/api/auth/login', {
      method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify(payload)
    });
    if(res.ok){
      // 서버가 HttpOnly 쿠키도 내려줌 ⇒ 페이지 새로고침으로 SSR 헤더 반영
      location.href = '/';
    }else{
      const err = await res.json().catch(()=>({message:'로그인 실패'}));
      alert(err.message || '로그인 실패');
    }
  });
}


if (signupPageForm){
  signupPageForm.addEventListener('submit', async (e)=>{
    e.preventDefault();
    const fd = new FormData(signupPageForm);
    const pw  = fd.get('memberPassword');
    const pw2 = fd.get('memberPasswordConfirm');
    if(pw !== pw2){
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }
    const payload = {
      memberEmail: fd.get('memberEmail'),
      memberPassword: pw
    };
    const res = await fetch('/api/auth/signup', {
      method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify(payload)
    });
    if(res.ok){
      alert('
