
package org.example.last.java.controller;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.example.last.java.entity.*;
import org.example.last.java.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("api")
@Slf4j
@Api(tags = "员工相关接口")
public class test {
    @Autowired
    EmployeeServiceImpl employeeService;
    @Autowired
    SportServiceimpl sportServiceimpl;
    @Autowired
    UserBookMessageimpl userBookMessageimpl;
    @Autowired
    AdminServiceimpl adminServiceimpl;
    @Autowired
    RaceService raceService;

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/userInfo")
    public int login(@RequestBody Employee employeeDTO) {
        String password = employeeDTO.getPassword();
        String username = employeeDTO.getUsername();
        System.out.println(employeeDTO);
        Employee employee = employeeService.login(employeeDTO);
        if (employee == null) {
            return 0;
        }
        if (employee.getPassword().equals(password)) {

            return 1;

        }
        return 0;
    }

    @PostMapping("/userInfo/ppq")
    public List<SportDto> getsport() {
        System.out.println("success");
        List<SportDto> ljs = new ArrayList<>();
        ljs = sportServiceimpl.SelectAllppq();
        return ljs;
    }

    @PostMapping("/userInfo/ymq")
    public List<SportDto> getsport1() {
        System.out.println("success");
        List<SportDto> ljs = new ArrayList<>();
        ljs = sportServiceimpl.SelectAllymq();
        return ljs;
    }

    @PostMapping("/userInfo/wq")
    public List<SportDto> getsport2() {
        System.out.println("success");
        List<SportDto> ljs = new ArrayList<>();
        ljs = sportServiceimpl.SelectAllwq();
        return ljs;
    }

    @PostMapping("/userInfo/book")
    public int UpdateSport(@RequestBody UpdateMessage updateMessage) {
        System.out.println(updateMessage);
        int flag = 0;
        int time = updateMessage.getTime();
        int id = updateMessage.getId();
        if (updateMessage.getIsBook()) {
            if (updateMessage.getType().equals("羽毛球")) {
                flag = sportServiceimpl.UpdateSportymq(id, time);
                Message message = new Message(updateMessage);
                userBookMessageimpl.UpdateMessage(message);
                return flag;
            }
            if (updateMessage.getType().equals("乒乓球")) {
                flag = sportServiceimpl.UpdateSportppq(id, time);
                Message message = new Message(updateMessage);
                userBookMessageimpl.UpdateMessage(message);
                return flag;
            }
            if (updateMessage.getType().equals("网球")) {
                flag = sportServiceimpl.UpdateSportwq(id, time);
                Message message = new Message(updateMessage);
                userBookMessageimpl.UpdateMessage(message);
                return flag;
            }

        } else {
            if (updateMessage.getType().equals("羽毛球")) {
                flag = sportServiceimpl.CancleUpdateSportymq(id, time);
                Message message = new Message(updateMessage);
                userBookMessageimpl.CancleUpdateMessage(message);
                return flag;
            }
            if (updateMessage.getType().equals("乒乓球")) {
                flag = sportServiceimpl.CancleUpdateSportppq(id, time);
                Message message = new Message(updateMessage);
                userBookMessageimpl.CancleUpdateMessage(message);
                return flag;
            }
            if (updateMessage.getType().equals("网球")) {
                flag = sportServiceimpl.CancleUpdateSportwq(id, time);
                Message message = new Message(updateMessage);
                userBookMessageimpl.CancleUpdateMessage(message);
                return flag;
            }
        }
        return 0;
    }

    @PostMapping("/adminInfo")
    public int Adminlogin(@RequestBody Admin admin) {
        System.out.println(admin.adminName + "登陆成功");
        String id = admin.getAdminName();
        String password = admin.getAdminPassword();
        Admin admin1 = adminServiceimpl.login(id);
        if (password.equals(admin1.getAdminPassword())) {
            return 1;
        } else {
            return 0;
        }
    }

    @PostMapping("/admin/sport")
    public List<MessageDto> MessageSelect(@RequestBody UserMessageId userMessageId) {

        List<MessageDto> ljs = userBookMessageimpl.AdminSelectUser(userMessageId);
        return ljs;
    }

    @PostMapping("/admin/delete")
    public int deleteSportDateMessage(@RequestBody MessageDto messageDto) {
        System.out.println(messageDto);
        userBookMessageimpl.deleteDataMessage(messageDto);
        sportServiceimpl.CancleUpdateSportppq(messageDto.SportId, messageDto.time - 7);
        return 1;
    }

    @PostMapping("/userInfo/sport/Race")
    public List<RaceLjs> SelectAllRace() {
        List<RaceLjs> ljs = raceService.SelectRace();
        return ljs;
    }

    @PostMapping("/userInfo/sport/Race/BaoMing")
    public int RacerBaoming(@RequestBody RacerMessage racerMessage) {
        System.out.println(racerMessage);
        int RaceId = racerMessage.eventId;
        String UserId = racerMessage.userId;
        raceService.BaoMing(UserId, RaceId);
        return 1;
    }

    @PostMapping("/person")
    public RacerBaoMing PersonSelect(@RequestBody RacerMessage racerMessage1) {
        String UserId = racerMessage1.getUserId();
        List<RacerBaoMing> ljs = raceService.ShowRacerMessage(UserId);
        RacerBaoMing racerBaoMing = ljs.get(0);
        return racerBaoMing;
    }

    @PostMapping("/userInfo/sport/Race/QuXiaoBaoMing")
    public int QuxiaoBaoMing(@RequestBody RacerMessage racerMessage) {
        System.out.println("quxiao" + racerMessage);
        raceService.QuxiaoBaoMing(racerMessage);
        return 1;
    }

    @PostMapping("/person/race")
    public List<RacerBaoMing> RacerBaoingSelect(@RequestBody RacerMessage racerMessage1) {
        String UserId = racerMessage1.getUserId();
        System.out.println(UserId + "success");
        List<RacerBaoMing> ljs = raceService.ShowRacerMessage(UserId);
        return ljs;
    }

    @PostMapping("/admin/insert/race")
    public int InsertRace(@RequestBody RaceLjs raceLjs) {
        System.out.println(raceLjs);
        raceService.insertRace(raceLjs);
        return 1;
    }

    @PostMapping("/admin/delete/race")
    public int DeleteRace(@RequestBody RaceLjs raceLjs) {
        System.out.println(raceLjs);
        raceService.deleteRace(raceLjs);
        return 1;
    }

    @PostMapping("/admin/update/race")
    public int UpdateRace(@RequestBody RaceLjs raceLjs) {
        System.out.println(raceLjs);
        raceService.updateRace(raceLjs);
        return 1;
    }

    @PostMapping("/adminInfo/enableColumn/ppq")
    public int UpdateSportppq2(@RequestBody AdminSport adminSport) {
        sportServiceimpl.AdminPpq2(adminSport.fieldId);
        return 1;
    }

    @PostMapping("/adminInfo/disenableColumn/ppq")
    public int UpdateSportppq1(@RequestBody AdminSport adminSport) {
        System.out.println(adminSport + "ssssss");
        sportServiceimpl.AdminPpq1(adminSport.fieldId);
        return 1;
    }

    @PostMapping("/adminInfo/race/result")
    public void ResultRace(@RequestBody ResultRace resultRace) {
        System.out.println(resultRace);
        raceService.UpdateRaceResult(resultRace);
    }

    @PostMapping("/UserInfo/race/results")
    public ResultRace ResultRaceSelect(@RequestBody ResultRace resultRace) {
        String RaceName = resultRace.getRaceName();
        System.out.println(resultRace);
        ResultRace lsj = raceService.SelectRaceResult(RaceName);
        System.out.println(lsj);
        return lsj;
    }

    @PostMapping("/admin/payment")
    public int Payment(@RequestBody MessageDto messageDto) {
        userBookMessageimpl.deleteDataMessage(messageDto);
        return 1;
    }

    @PostMapping("/admin/disable")
    public int Punish(@RequestBody Employee employee) {
        System.out.println(employee);
        employeeService.Punish(employee);
        return 1;
    }

    @PostMapping("/admin/blacklist")
    public int enable(@RequestBody Employee employee) {
        employeeService.enable(employee);
        return 1;
    }
    @PostMapping("/admin/disabled-status")
    public int SearchStatus(@RequestBody Test test){
        System.out.println("sssss"+test);
        int Status;
        Status=employeeService.SelectStatus(test);
        System.out.println(Status);
        return Status;
    }

}
