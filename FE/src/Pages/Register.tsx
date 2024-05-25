import { useState } from "react";
import { Link } from "react-router-dom";
import { checkPasswordLength, validateEmail,checkPhoneNumber } from "../Ultis/Validation";
import { toast } from "react-toastify";
import { register } from "../apis/service";
import { useNavigate } from "react-router-dom";
export default function Register() {
  const [email, setEmail] = useState("");
  const [username, setUserName] = useState("");
  const [phone, setPhone] = useState("");
  const [fullName, setFullName] = useState("");
  const [address, setAddress] = useState("");
  const [password, setPass] = useState("");
  const [rpassword, setRPass] = useState("");
  const navigate = useNavigate();
  const Register = async () => {
    if (!validateEmail(email)) {
      toast.warning("Email sai cú pháp");
      return;
    }

    if (!checkPasswordLength(password)) {
      toast.warning("Mật khẩu phải lớn hơn hoặc bằng 6 kí tự");
      return;
    }
    if (!checkPhoneNumber(phone)){
      toast.warning("Số điện thoại không đúng định dạng");
      return;
    }

    if (password != rpassword) {
      toast.warning("Mật khẩu không giống nhau");
      return;
    }
    const res = await register({
      email: email,
      password: password,
      phoneNumber: phone,
      fullName:fullName,
      address:address
    });
    if (res?.type != "error") {
      navigate("/signin");
    }
  };
  return (
    <section className="bg-gray-50 dark:bg-gray-900">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-[calc(100vh-64px)] lg:py-0">
        <a
          href="#"
          className="flex items-center mb-6 text-2xl font-semibold text-gray-900 "
        >
          <img
            className="w-8 h-8 mr-2"
            src="https://flowbite.s3.amazonaws.com/blocks/marketing-ui/logo.svg"
            alt="logo"
          />
          Flowbite
        </a>
        <div className="w-full bg-white rounded-lg shadow border-2 border-gray-300 md:mt-0 sm:max-w-md xl:p-0 ">
          <div className="p-6 space-y-4 md:space-y-6 sm:p-8 ">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl ">
              Create new account
            </h1>
            <form className="space-y-4 md:space-y-6" action="#">
              <div>
                <label
                  form="name"
                  className="block mb-2 text-sm font-medium text-gray-900 "
                >
                  Your user name
                </label>
                <input
                  onChange={(event) => {
                    setUserName(event.target.value);
                  }}
                  type="text"
                  name="name"
                  id="name"
                  className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 "
                  placeholder="minhieu262"
                  required
                />
              </div>
              <div>
                <label
                  form="name"
                  className="block mb-2 text-sm font-medium text-gray-900 "
                >
                  Your Full Name
                </label>
                <input
                  onChange={(event) => {
                    setFullName(event.target.value);
                  }}
                  type="text"
                  name="name"
                  id="name"
                  className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 "
                  placeholder="Full Name"
                  required
                />
              </div>
              <div>
                <label
                  form="name"
                  className="block mb-2 text-sm font-medium text-gray-900 "
                >
                  Your Address
                </label>
                <input
                  onChange={(event) => {
                    setAddress(event.target.value);
                  }}
                  type="text"
                  name="name"
                  id="name"
                  className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 "
                  placeholder="Address"
                  required
                />
              </div>
              <div>
                <label
                  form="name"
                  className="block mb-2 text-sm font-medium text-gray-900 "
                >
                  Phone Number
                </label>
                <input
                  onChange={(event) => {
                    setPhone(event.target.value);
                  }}
                  type="text"
                  name="name"
                  id="name"
                  className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 "
                  placeholder="Phone Number"
                  required
                />
              </div>
              <div>
                <label
                  form="email"
                  className="block mb-2 text-sm font-medium text-gray-900 "
                >
                  Your email
                </label>
                <input
                  onChange={(event) => {
                    setEmail(event.target.value);
                  }}
                  type="email"
                  name="email"
                  id="email"
                  className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 "
                  placeholder="Email"
                  required
                />
              </div>
              <div>
                <label
                  form="password"
                  className="block mb-2 text-sm font-medium text-gray-900 "
                >
                  Password
                </label>
                <input
                  onChange={(event) => {
                    setPass(event.target.value);
                  }}
                  type="password"
                  name="password"
                  id="password"
                  placeholder="••••••••"
                  className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 "
                  required
                />
              </div>
              <div>
                <label
                  form="password"
                  className="block mb-2 text-sm font-medium text-gray-900 "
                >
                  Comfirm Password
                </label>
                <input
                  onChange={(event) => {
                    setRPass(event.target.value);
                  }}
                  type="comfirmPassword"
                  name="comfirmPassword"
                  id="comfirmPassword"
                  placeholder="••••••••"
                  className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 "
                  required
                />
              </div>
              <button
                onClick={Register}
                type="submit"
                className="w-full text-white bg-blue-600 hover:bg-blue-700  focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center "
              >
                Sign up
              </button>
              <p className="text-sm font-light text-gray-500 ">
                Already have account?{" "}
                <Link
                  to="/signin"
                  className="font-medium text-primary-600 hover:underline "
                >
                  Sign in
                </Link>
              </p>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
}
