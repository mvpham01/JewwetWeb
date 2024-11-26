package com.example.jewelryWeb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jewelryWeb.models.DTO.DiamondFilterDTO;
import com.example.jewelryWeb.models.Entity.Diamond;
import com.example.jewelryWeb.repository.DiamondRepository;

@Service
public class DiamondService {
@Autowired
    private DiamondRepository diamondRepository;

    public List<Diamond> getAllDiamonds() {
        return diamondRepository.findAll();
    }

    public Optional<Diamond> getDiamondById(Long diamondId) {
        return diamondRepository.findById(diamondId);
    }
    public Diamond addDiamond(Diamond diamond) {
        return diamondRepository.save(diamond);
    }

    public Diamond updateDiamond(Long diamondId, Diamond updatedDiamond) {
        return diamondRepository.findById(diamondId).map(diamond -> {
            diamond.setWeight(updatedDiamond.getWeight());
            diamond.setSize(updatedDiamond.getSize());
            diamond.setColorGrade(updatedDiamond.getColorGrade());
            diamond.setClarity(updatedDiamond.getClarity());
            diamond.setCutting(updatedDiamond.getCutting());
            diamond.setPrice(updatedDiamond.getPrice());
            return diamondRepository.save(diamond);
        }).orElseThrow(() -> new IllegalArgumentException("Diamond not found with id: " + diamondId));
    }

    public void deleteDiamond(Long diamondId) {
        diamondRepository.deleteById(diamondId);
    }
    public List<Diamond> filterDiamonds(DiamondFilterDTO filterDTO) {
        return diamondRepository.filterDiamonds(
                filterDTO.getMinWeight(),
                filterDTO.getMaxWeight(),
                filterDTO.getMinPrice(),
                filterDTO.getMaxPrice(),
                filterDTO.getSize(),
                filterDTO.getColorGrade(),
                filterDTO.getClarity()
        );
    }
}