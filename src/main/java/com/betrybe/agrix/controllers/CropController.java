package com.betrybe.agrix.controllers;

import com.betrybe.agrix.dtos.CropDto;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.services.CropService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class represents a crop controller.
 */
@RestController
@RequestMapping("/crops")
public class CropController {

  @Autowired
  private CropService cropService;

  /**
   * This method gets all crops.
   */
  @GetMapping
  public ResponseEntity<List<CropDto>> getAllCrops() {
    List<CropDto> crops = cropService.getAllCrops();
    return ResponseEntity.status(HttpStatus.OK).body(crops);
  }

  /**
   * This method gets a crop by id.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Object> getCropById(@PathVariable("id") Integer id) {
    Optional<CropDto> cropOptional = cropService.findById(id);

    if (cropOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.OK).body(cropOptional.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plantação não encontrada!");
    }
  }

  /**
   * This method searchs crops by harvest date.
   */
  @GetMapping("/search")
  public ResponseEntity<List<CropDto>> searchCropsByHarvestDate(
      @RequestParam("start")LocalDate startDate,
      @RequestParam("end")LocalDate endDate) {
    List<Crop> crops = cropService.findCropsByHarvestDate(startDate, endDate);

    if (crops.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
    } else {
      List<CropDto> cropDtos = crops.stream()
          .map(CropDto::fromCrop)
          .toList();
      return ResponseEntity.status(HttpStatus.OK).body(cropDtos);
    }

  }

}
