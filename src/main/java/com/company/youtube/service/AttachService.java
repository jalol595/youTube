package com.company.youtube.service;


import com.company.youtube.config.CustomUserDetails;
import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.entity.AttachEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.exseptions.BadRequestException;
import com.company.youtube.exseptions.ItemNotFoundEseption;
import com.company.youtube.repository.AttachRepository;
import com.company.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AttachService {

    @Value("${attach.folder}")
    private String attachFolder;


    @Value("${server.url}")
    private String serverUrl;

    @Autowired
    private AttachRepository attachRepository;

    public String getAttachOpenUrl(String uuid){
        return   serverUrl + "attach/open?fileId=" + uuid;
    }

    public AttachDTO saveToSystem(MultipartFile file) {
        try {

            String pathFolder = getYmDString(); // 2022/06/20
            String extension = getExtension(file.getOriginalFilename()); // jpg

            AttachEntity attachEntity = new AttachEntity();
            attachEntity.setOrginalName(file.getOriginalFilename());
            attachEntity.setExtensional(extension);
            attachEntity.setSize(file.getSize());
            attachEntity.setPath(pathFolder);
            attachRepository.save(attachEntity);

            String fileName = attachEntity.getId() + "." + extension; //  asdas-dasdas-dasdasd-adadsd.jpg
            File folder = new File(attachFolder + pathFolder); // attaches/2022/06/20
            if (!folder.exists()) {
                folder.mkdirs();
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachFolder + pathFolder + "/" + attachEntity.getId() + "." + extension);
            Files.write(path, bytes);

            AttachDTO attachDTO=new AttachDTO();
            attachDTO.setId(attachEntity.getId());
            attachDTO.setUrl(serverUrl+"attache/open/" +attachEntity.getId());

            return attachDTO;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] open_general(String id) {
        byte[] data;

        Optional<AttachEntity> attach = attachRepository.findById(id);
        if (attach.isEmpty()) {
            throw new ItemNotFoundEseption("not found");
        }

        AttachEntity attachEntity = attach.get();

        try {
            String path = attachFolder + attachEntity.getPath() + "/" + attachEntity.getId() + "." + attachEntity.getExtensional();
            Path file = Paths.get(path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public Resource download(String id) {

        Optional<AttachEntity> attach = attachRepository.findById(id);
        if (attach.isEmpty()){
            throw new ItemNotFoundEseption("not found");
        }

        AttachEntity attachEntity = attach.get();

        try {
            String path = attachFolder + attachEntity.getPath() + "/" + attachEntity.getId() + "." + attachEntity.getExtensional();

            Path file = Paths.get(path);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public String getYmDString() {

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public String delete(String id) {

        Optional<AttachEntity> attach = attachRepository.findById(id);
        if (attach.isEmpty()){
            throw new ItemNotFoundEseption("not found");
        }

        AttachEntity attachEntity = attach.get();
        attachRepository.deleteById(id);


        String path = attachFolder + attachEntity.getPath() + "/" + attachEntity.getId() + "." + attachEntity.getExtensional();

        try {
            Files.delete(Path.of(path));
        }catch (Exception e){
            e.printStackTrace();
        }

        return "succsess";
    }

    public byte[] loadImage(String id) {
        byte[] imageInByte;
        String path = getFileFullPath(get(id));

        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(new File(path));
        } catch (Exception e) {
            return new byte[0];
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(originalImage, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageInByte;
    }

    private String getFileFullPath(AttachEntity entity) {
        return attachFolder + entity.getPath() + "/" + entity.getId() + "." + entity.getExtensional();
    }

    public PageImpl paginationAttach(int page, int size) {

        ProfileEntity profile = getProfile();
        if (!profile.getRole().name().equals("ROLE_ADMIN")){
            throw new BadRequestException("not permish");
        }

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AttachEntity> all = attachRepository.findAll(pageable);

        List<AttachDTO> dtoList = new LinkedList<>();

        all.getContent().forEach(attachEntity -> {
            AttachDTO dto = new AttachDTO();
            dto.setOrginalName(attachEntity.getOrginalName());
            dto.setSize(attachEntity.getSize());
            dto.setUrl(serverUrl + "" + "attache/open/" + attachEntity.getId());
            dtoList.add(dto);
        });

        return new PageImpl(dtoList,pageable, all.getTotalElements());
    }

    public boolean existByPhoto(String id){
        boolean exists = attachRepository.existsById(id);
        if (exists){
            return true;
        }
        return false;
    }

    public AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundEseption("attach not found ");
        });
    }

    public ProfileEntity getProfile() {
        CustomUserDetails user = SpringSecurityUtil.getCurrentUser();
        return user.getProfile();
    }


}
