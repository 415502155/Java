/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package tk.mybatis.springboot.model;

/***
 * {@literal}
 * @author Administrator
 * t_city_info
 * 城市详情表
 */
public class CityInfo {
    private Integer city_id;

    private String city_name;
    
    private Integer province_id;
    
    private Integer long_city_id;
    
    private String city_address;
    
    private String city_phone;
    
    private String terminal_password;

    private Integer work_status;

	public Integer getCity_id() {
		return city_id;
	}

	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public Integer getProvince_id() {
		return province_id;
	}

	public void setProvince_id(Integer province_id) {
		this.province_id = province_id;
	}

	public Integer getLong_city_id() {
		return long_city_id;
	}

	public void setLong_city_id(Integer long_city_id) {
		this.long_city_id = long_city_id;
	}

	public String getCity_address() {
		return city_address;
	}

	public void setCity_address(String city_address) {
		this.city_address = city_address;
	}

	public String getCity_phone() {
		return city_phone;
	}

	public void setCity_phone(String city_phone) {
		this.city_phone = city_phone;
	}

	public String getTerminal_password() {
		return terminal_password;
	}

	public void setTerminal_password(String terminal_password) {
		this.terminal_password = terminal_password;
	}

	public Integer getWork_status() {
		return work_status;
	}

	public void setWork_status(Integer work_status) {
		this.work_status = work_status;
	}
    
    
}
