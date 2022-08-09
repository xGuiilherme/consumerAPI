package restapi.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.consumer.convertFilme.FilmeConverter;
import restapi.consumer.mapJson.FilmeJson;
import restapi.consumer.dto.FilmeDTO;
import restapi.consumer.service.FilmeService;
import restapi.consumer.vo.FilmeVO;


import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "api/filme")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private FilmeVO filmeVO;
    @Autowired
    private FilmeConverter filmeConverter;

    @GetMapping("/{tema}")
    public ResponseEntity<FilmeJson> getFilme(@PathVariable String tema) {
        try {
            FilmeJson filmeJson = filmeService.getFilme(tema);
            return ResponseEntity.status(HttpStatus.OK).body(filmeJson);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<FilmeVO> salveFilme(@RequestBody FilmeDTO filmeDTO) {
        try {
            FilmeVO filmeVO = filmeConverter.converteParaFilmeVO(filmeService.save(filmeDTO));

            addHateaos(filmeVO);

            return ResponseEntity.status(HttpStatus.CREATED).body(filmeVO);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void addHateaos(FilmeVO filmeVO) {
        filmeVO.add(linkTo(methodOn(FilmeController.class).getById(filmeVO.getId()))
                .withSelfRel());
    }
}