const signUpButton = document.getElementById("signUp");
const signInButton = document.getElementById("signIn");
const container = document.getElementById("container");

const inputUsername = document.getElementById("input_user_name");

const inputPassword = document.getElementById("input_password");

const isRegister = document.getElementById("isRegister");

if (isRegister.value == "true") {
  container.classList.add("right-panel-active");
}

signUpButton.addEventListener("click", () => {
  container.classList.add("right-panel-active");
});

signInButton.addEventListener("click", () => {
  container.classList.remove("right-panel-active");
});

const formLogIn = document.getElementById("form_log_in");

const buttonSubmitForm = formLogIn.getElementsByClassName("button_submit_form");

const listFormControl = formLogIn.getElementsByClassName("form_control");

let sizeFormControl = listFormControl.length;

function validationInputForm() {
  let isValid = true;
  for (let i = 0; i < sizeFormControl; ++i) {
    const elementFormControl = listFormControl[i];
    const inputForm = elementFormControl.getElementsByClassName("input_form");
    const validFeedback =
      elementFormControl.getElementsByClassName("valid_feedback");
    if (inputForm[0].value === "") {
      isValid = false;
      validFeedback[0].classList.remove("d-none");
    }
  }
  return isValid;
}

buttonSubmitForm[0].addEventListener("click", function (e) {
  let isValid = validationInputForm();
  if (!isValid) {
    e.preventDefault();
  }
});
