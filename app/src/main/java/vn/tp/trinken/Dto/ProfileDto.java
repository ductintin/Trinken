package vn.tp.trinken.Dto;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.ByteString;
import retrofit2.http.Multipart;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private String userName;

    private String firstName;

    private String lastName;

    private String gender;

    private String email;

    private String phoneNumber;

    private String address;

    private File imageFile;

    public Map<String, RequestBody> profile(){
        Map<String, RequestBody> profile = new HashMap<>();
        if(userName!=null){
            profile.put("userName", RequestBody.create(MediaType.parse("text/plain"), userName));
        }

        profile.put("firstName", RequestBody.create(MediaType.parse("text/plain"), firstName));
        profile.put("lastName", RequestBody.create(MediaType.parse("text/plain"), lastName));
        profile.put("gender", RequestBody.create(MediaType.parse("text/plain"), gender));
        if(email!=null){
            profile.put("email", RequestBody.create(MediaType.parse("text/plain"), email));
        }
        profile.put("phoneNumber", RequestBody.create(MediaType.parse("text/plain"), phoneNumber));
        profile.put("address", RequestBody.create(MediaType.parse("text/plain"), address));
        if (imageFile != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            profile.put("imageFile\"; filename=\"" + imageFile.getName() + "\"", requestBody);
        }
        return profile;
    }
}
